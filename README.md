# ⚔️ CombatGuard

**A lightweight and configurable combat tag plugin for Minecraft SMP servers.**
Prevent combat logging and track player stats with a clean and simple system.

https://modrinth.com/plugin/combat-guard-gregwll

---

## ✨ Features

### ⚔️ Combat Tag System
When two players fight, both are tagged for a configurable duration. A progress bar displayed on the action bar shows the remaining combat time in real time.

### 🚪 Combat Logout Punishment
If a tagged player disconnects, they are automatically punished. Choose between an instant kill, a temporary ban, or both — all configurable.

### 📊 Stats Tracking
Every kill, death and combat logout is tracked per player and saved in individual JSON files. Stats are persistent across restarts.

### 🗂️ Tab List Stats
Optionally display each player's kills and deaths directly in the tab list — toggleable in the config.

### ⚙️ Full Config Support
Customize the prefix, combat duration, punishment type, ban duration, and tab stats display. Reload without restarting with `/combatguard reload`.

---

## 📋 Commands

| Command | Description |
|---|---|
| `/stats <player>` | Display a player's combat stats |
| `/combatguard reload` | Reload the config |

---

## 🔐 Permissions

| Permission | Description |
|---|---|
| `combatguard.admin` | Access to `/combatguard reload` |

---

## ⚙️ Config

```yaml
# Plugin prefix
prefix: "CombatGuard "

# Combat tag duration in seconds
combat-duration: 15

# Punishment on combat logout: kill | ban | both
punishment: kill

# Ban duration in seconds (only if punishment is "ban" or "both")
ban-duration: 300

# Show kills/deaths in the tab list
tab-stats: true
```

---

## 📦 Installation
Drop the `.jar` into your `plugins/` folder and restart your server. No dependencies required.

---

## 🔧 Compatibility
- ✅ Spigot / Paper
- ✅ Minecraft 1.21.x

---

*Made with ❤️ by Gregwll*
