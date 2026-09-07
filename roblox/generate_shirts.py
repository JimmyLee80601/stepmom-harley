"""
Roblox Meme Shirt Generator for Trystan
Generates upload-ready Roblox shirt PNGs (585x559)
Each design is a meme that kids would actually buy
"""

from PIL import Image, ImageDraw, ImageFont
import os
import random

# Roblox shirt template size
WIDTH = 585
HEIGHT = 559

# Output directory
OUTPUT_DIR = os.path.join(os.path.dirname(__file__), "shirts")
os.makedirs(OUTPUT_DIR, exist_ok=True)

# Color palettes (kid-friendly, bold, eye-catching)
PALETTES = [
    {"bg": (30, 30, 30), "text": (255, 255, 255), "accent": (0, 200, 83)},    # Dark + green
    {"bg": (255, 69, 58), "text": (255, 255, 255), "accent": (255, 214, 10)},  # Red + yellow
    {"bg": (0, 122, 255), "text": (255, 255, 255), "accent": (255, 255, 255)}, # Blue + white
    {"bg": (88, 86, 214), "text": (255, 255, 255), "accent": (255, 204, 0)},   # Purple + gold
    {"bg": (50, 50, 50), "text": (0, 255, 127), "accent": (0, 255, 127)},      # Matrix green
    {"bg": (255, 149, 0), "text": (0, 0, 0), "accent": (255, 255, 255)},       # Orange + black
    {"bg": (0, 199, 190), "text": (0, 0, 0), "accent": (255, 255, 255)},       # Teal
    {"bg": (175, 82, 222), "text": (255, 255, 255), "accent": (255, 204, 0)},   # Purple + yellow
    {"bg": (255, 45, 85), "text": (255, 255, 255), "accent": (255, 255, 255)},  # Pink
    {"bg": (0, 0, 0), "text": (255, 255, 255), "accent": (255, 0, 0)},         # Black + red
]

# Meme designs: (top_text, bottom_text, emoji_or_symbol)
MEMES = [
    ("GG", "NO RE", "🎮"),
    ("BACON", "HAIR", "🥓"),
    ("NOOB", "DOWN", "💀"),
    ("ROBLOX", "> HOMEWORK", "📚"),
    ("OOF", "", "😤"),
    ("FREE", "ROBUX", "💰"),
    ("I survived", "the server", "🔥"),
    ("RICH", "OR BROKE", "💸"),
    ("THIS IS", "FINE", "🔥"),
    ("TRUST", "NO ONE", "🎭"),
    ("AFK", "", "💤"),
    ("LAG", "IS REAL", "📡"),
    ("NO CAP", "", "🧢"),
    ("SLAY", "", "⭐"),
    ("MAIN", "CHARACTER", "👑"),
    ("SUS", "", "ඞ"),
    ("W", "MENTALITY", "🏆"),
    ("IT'S Giving", "ROBLOX", "✨"),
    ("built", "DIFFERENT", "💪"),
    ("Rent Free", "", "🧠"),
    ("NPC", "ENERGY", "🤖"),
    ("GOATED", "", "🐐"),
    ("BALLER", "", "🏀"),
    ("CRACKED", "", "💎"),
]

def get_font(size, bold=True):
    """Get a good font for the shirt."""
    font_paths = [
        "C:\\Windows\\Fonts\\arialbd.ttf" if bold else "C:\\Windows\\Fonts\\arial.ttf",
        "C:\\Windows\\Fonts\\calibrib.ttf",
        "C:\\Windows\\Fonts\\ariblk.ttf",
    ]
    for fp in font_paths:
        if os.path.exists(fp):
            try:
                return ImageFont.truetype(fp, size)
            except:
                continue
    return ImageFont.load_default()

def draw_text_with_outline(draw, position, text, font, fill, outline_color=(0,0,0), outline_width=3):
    """Draw text with outline for that classic meme look."""
    x, y = position
    # Draw outline
    for dx in range(-outline_width, outline_width + 1):
        for dy in range(-outline_width, outline_width + 1):
            if dx*dx + dy*dy <= outline_width*outline_width:
                draw.text((x + dx, y + dy), text, font=font, fill=outline_color, anchor="mm")
    # Draw main text
    draw.text((x, y), text, font=font, fill=fill, anchor="mm")

def generate_shirt(design_num, top_text, bottom_text, palette):
    """Generate a single meme shirt."""
    img = Image.new("RGBA", (WIDTH, HEIGHT), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Front panel (center of template) - approximately 225x280 in the middle
    front_x, front_y = 180, 30
    front_w, front_h = 225, 280

    # Fill the front panel with background color
    bg = palette["bg"]
    draw.rounded_rectangle(
        [front_x, front_y, front_x + front_w, front_y + front_h],
        radius=8,
        fill=bg
    )

    # Add a subtle pattern/stripe
    accent = palette["accent"]
    stripe_y = front_y + front_h // 2
    draw.rectangle(
        [front_x, stripe_y - 2, front_x + front_w, stripe_y + 2],
        fill=(*accent, 80)
    )

    # Top text (larger)
    if top_text:
        font_size = 36 if len(top_text) <= 10 else 28
        font = get_font(font_size, bold=True)
        text_color = palette["text"]
        draw_text_with_outline(
            draw,
            (front_x + front_w // 2, front_y + front_h // 3),
            top_text.upper(),
            font,
            text_color,
            outline_color=(0, 0, 0),
            outline_width=3
        )

    # Bottom text
    if bottom_text:
        font_size = 28 if len(bottom_text) <= 10 else 22
        font = get_font(font_size, bold=True)
        text_color = palette["accent"]
        draw_text_with_outline(
            draw,
            (front_x + front_w // 2, front_y + front_h * 2 // 3),
            bottom_text.upper(),
            font,
            text_color,
            outline_color=(0, 0, 0),
            outline_width=2
        )

    # Add side panels (visible on avatar)
    side_color = (*bg, 200)
    # Left side
    draw.rectangle([130, 30, 180, 310], fill=side_color)
    # Right side
    draw.rectangle([405, 30, 455, 310], fill=side_color)

    # Save
    filename = f"shirt_{design_num:02d}_{top_text.replace(' ', '_').lower()}.png"
    filepath = os.path.join(OUTPUT_DIR, filename)
    img.save(filepath, "PNG")
    print(f"  Created: {filename}")
    return filepath

def main():
    print("ROBLOX MEME SHIRT GENERATOR FOR TRYSTAN")
    print("=" * 50)
    print(f"Generating {len(MEMES)} meme shirts...")
    print()

    generated = []
    for i, (top, bottom, emoji) in enumerate(MEMES):
        palette = PALETTES[i % len(PALETTES)]
        path = generate_shirt(i + 1, top, bottom, palette)
        generated.append(path)

    print()
    print(f"Generated {len(generated)} shirts in: {OUTPUT_DIR}")
    print()
    print("HOW TO UPLOAD TO ROBLOX:")
    print("  1. Open Roblox Studio")
    print("  2. Go to Avatar > Shirts")
    print("  3. Click 'Create a Shirt'")
    print("  4. Upload each .png file")
    print("  5. Set a price (10-50 Robux recommended)")
    print("  6. Publish!")
    print()
    print("TIPS:")
    print("  - Name them something catchy")
    print("  - Add a description with keywords")
    print("  - Price between 10-50 Robux for best sales")
    print("  - The bacon hair crowd loves bold text memes")

if __name__ == "__main__":
    main()