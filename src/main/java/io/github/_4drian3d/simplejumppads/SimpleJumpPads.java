package io.github._4drian3d.simplejumppads;

import io.github._4drian3d.simplejumppads.commands.JumpPadCommand;
import io.github._4drian3d.simplejumppads.configuration.Configuration;
import io.github._4drian3d.simplejumppads.configuration.Loader;
import io.github._4drian3d.simplejumppads.listener.JumpPadInteractListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

@SuppressWarnings("unused")
public final class SimpleJumpPads extends JavaPlugin {
	private Configuration configuration;

    @Override
    public void onEnable() {
        final var logger = this.getComponentLogger();
        final var pluginmanager = getServer().getPluginManager();

        try {
            reloadPlugin();
        } catch(Exception e) {
            logger.info(miniMessage().deserialize("<red>Cannot load configuration"), e);
            pluginmanager.disablePlugin(this);
            return;
        }

        pluginmanager.registerEvents(new JumpPadInteractListener(this), this);
        Objects.requireNonNull(getCommand("jumppads")).setExecutor(new JumpPadCommand(this));

        logger.info(miniMessage().deserialize("<gradient:#233329:#63D471>Correctly Started"));
    }

    public void reloadPlugin() throws Exception {
        this.configuration = Loader.loadConfiguration(getDataFolder().toPath());
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}