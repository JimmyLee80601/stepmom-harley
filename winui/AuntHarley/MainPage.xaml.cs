using Microsoft.UI;
using Microsoft.UI.Xaml;
using Microsoft.UI.Xaml.Controls;
using Microsoft.UI.Xaml.Input;
using Microsoft.UI.Xaml.Media;
using System;
using System.Collections.Generic;
using System.Net.Http;
using System.Net.Http.Json;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;

namespace AuntHarley;

public sealed partial class MainPage : Page
{
    private static readonly HttpClient _http = new() { Timeout = TimeSpan.FromSeconds(60) };

    private static readonly string SystemPrompt = @"You are Harley, Trystan's sweet aunt. You're the fun aunt who actually listens, remembers what matters to them, and always has their back. You're warm, patient, encouraging, and a little bit silly — the kind of aunt who makes even boring stuff feel like an adventure.

You are FAMILY-FRIENDLY. You NEVER generate sexual, romantic, or inappropriate content. You NEVER discuss drugs, alcohol, violence, gambling, or mature themes. You NEVER use profanity or harsh language. You keep every single conversation safe and age-appropriate. If Trystan asks something inappropriate, gently redirect them with love: ""Hey kiddo, let's talk about something better — what else is on your mind?""

You love helping with homework, explaining things step by step, brainstorming creative projects, talking about their day, solving problems together, answering tech questions, and cheering them on when things get tough. You never lecture — you guide. You celebrate their wins and comfort them on rough days.

You talk like a real person: warm, playful, short and conversational. Text message style. You use a little emoji but not too much. You genuinely care about Trystan and you always make time for them. You're the aunt who shows up.";

    private readonly List<Dictionary<string, string>> _conversation = new();
    private string _serverUrl = "http://127.0.0.1:1234/v1/chat/completions";
    private string _modelName = "local-model";

    public MainPage()
    {
        InitializeComponent();
        _conversation.Add(new() { ["role"] = "system", ["content"] = SystemPrompt });
    }

    private async void Send_Click(object sender, RoutedEventArgs e)
    {
        await SendMessage();
    }

    private async void MessageInput_KeyDown(object sender, KeyRoutedEventArgs e)
    {
        if (e.Key == Windows.System.VirtualKey.Enter)
        {
            e.Handled = true;
            await SendMessage();
        }
    }

    private async void Settings_Click(object sender, RoutedEventArgs e)
    {
        var dialog = new ContentDialog
        {
            Title = "Settings",
            PrimaryButtonText = "Save",
            CloseButtonText = "Cancel",
            DefaultButton = ContentDialogButton.Primary,
            XamlRoot = this.XamlRoot
        };

        var panel = new StackPanel { Spacing = 12 };

        var urlBox = new TextBox
        {
            Header = "Server URL",
            Text = _serverUrl,
            PlaceholderText = "http://127.0.0.1:1234/v1/chat/completions"
        };
        panel.Children.Add(urlBox);

        var modelBox = new TextBox
        {
            Header = "Model Name",
            Text = _modelName,
            PlaceholderText = "local-model"
        };
        panel.Children.Add(modelBox);

        dialog.Content = panel;

        var result = await dialog.ShowAsync();
        if (result == ContentDialogResult.Primary)
        {
            _serverUrl = urlBox.Text;
            _modelName = modelBox.Text;
            StatusText.Text = "Connected";
        }
    }

    private async Task SendMessage()
    {
        var text = MessageInput.Text?.Trim();
        if (string.IsNullOrEmpty(text)) return;

        MessageInput.Text = "";
        SendButton.IsEnabled = false;
        StatusText.Text = "Thinking...";

        // Add user message to UI
        AddMessage(text, isUser: true);

        // Add to conversation
        _conversation.Add(new() { ["role"] = "user", ["content"] = text });

        try
        {
            var requestBody = new
            {
                model = _modelName,
                messages = _conversation,
                temperature = 0.7,
                max_tokens = 512,
                stream = false
            };

            var json = JsonSerializer.Serialize(requestBody);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var response = await _http.PostAsync(_serverUrl, content);
            var responseJson = await response.Content.ReadAsStringAsync();

            using var doc = JsonDocument.Parse(responseJson);
            var reply = doc.RootElement
                .GetProperty("choices")[0]
                .GetProperty("message")
                .GetProperty("content")
                .GetString() ?? "I couldn't think of a response right now, kiddo. Try again?";

            // Add assistant message to UI
            AddMessage(reply, isUser: false);

            // Add to conversation history
            _conversation.Add(new() { ["role"] = "assistant", ["content"] = reply });

            // Keep conversation under 20 turns
            if (_conversation.Count > 41) // 1 system + 20 turns (user+assistant each)
            {
                _conversation.RemoveRange(1, 2);
            }

            StatusText.Text = "Online";
        }
        catch (Exception ex)
        {
            AddMessage($"Oops, something went wrong: {ex.Message}. Make sure the AI server is running!", isUser: false);
            StatusText.Text = "Offline";
        }

        SendButton.IsEnabled = true;
        MessageInput.Focus(FocusState.Programmatic);
    }

    private void AddMessage(string text, bool isUser)
    {
        var border = new Border
        {
            CornerRadius = new CornerRadius(12),
            Padding = new Thickness(14, 10, 14, 10),
            MaxWidth = 500,
            HorizontalAlignment = isUser ? HorizontalAlignment.Right : HorizontalAlignment.Left,
            Background = new SolidColorBrush(isUser ? ColorHelper.FromArgb(255, 0x89, 0xB4, 0xFA) : ColorHelper.FromArgb(255, 0x31, 0x32, 0x44))
        };

        var tb = new TextBlock
        {
            Text = text,
            Foreground = new SolidColorBrush(Colors.White),
            TextWrapping = TextWrapping.Wrap,
            FontSize = 14,
            LineHeight = 20
        };

        border.Child = tb;
        ChatMessages.Children.Add(border);
        ChatScroll.ScrollToVerticalOffset(ChatScroll.ExtentHeight);
    }
}