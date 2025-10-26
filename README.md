# NoPortal - Portal Control for Minecraft


**NoPortal** is an extremely lightweight and straightforward Paper/Spigot plugin for Minecraft servers that gives administrators full control over whether players can enter the Nether or the End. With a simple command, portals can be dynamically enabled or disabled without requiring a server restart.

Ideal for survival events, hardcore challenges, or servers where access to certain dimensions needs to be restricted or time-gated.

## ✨ Features

- **Dynamic Control:** Enable or disable Nether and End portals on the fly.
- **Simple Command:** A single, easy-to-remember command (`/noportal`) for management.
- **Configurable:** Customize the message players see when access is blocked.
- **Permission-Based:** The command is protected by a permission (`noportal.admin`).
- **Extremely Lightweight:** No unnecessary features, ensuring minimal server impact.
- **Plug-and-Play:** Simply drop it into your `plugins` folder and you're ready to go.

## Installation

1.  Download the latest version from the [Releases page](https://github.com/YOUR-GITHUB-NAME/YOUR-REPO-NAME/releases).
2.  Place the downloaded `.jar` file into your server's `plugins` folder.
3.  Restart or reload your server.
4.  Done! The plugin is now active.

## ⚙️ Configuration

The configuration is automatically generated in `plugins/NoPortal/config.yml`.

```yml
# Configuration for the NoPortal plugin
portals:
  # Allow players to enter the Nether (true = yes, false = no)
  allow-nether: true
  # Allow players to enter the End (true = yes, false = no)
  allow-end: true

messages:
  # Message sent to players when a portal is disabled.
  # Supports color codes using '&'.
  block-message: "&cAccess to this dimension is disabled!"
```

## Commands and Permissions

| Command                               | Description                                   | Permission         |
| ------------------------------------- | --------------------------------------------- | ------------------ |
| `/noportal <nether\|end> <true\|false>` | Enables or disables access to a dimension. | `noportal.admin` |

- **Permission:** `noportal.admin`
  - Grants access to the `/noportal` command.
  - Defaults to OP (server operators) only.

### Example Usage:

-   **To block the Nether:** `/noportal nether false`
-   **To re-enable the End:** `/noportal end true`

## Building from Source (Optional)

If you wish to compile the plugin yourself:

1.  Clone this repository: `git clone https://github.com/YOUR-GITHUB-NAME/YOUR-REPO-NAME.git`
2.  Navigate into the directory: `cd YOUR-REPO-NAME`
3.  Build the project using Maven: `mvn clean package`
4.  The final `.jar` file will be located in the `target` directory.

## License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.
