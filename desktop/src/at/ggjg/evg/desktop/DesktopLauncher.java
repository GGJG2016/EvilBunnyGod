package at.ggjg.evg.desktop;

import at.ggjg.evg.helpers.Constants;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import at.ggjg.evg.EvilBunnyGodGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = Constants.VIEWPORT_GUI_WIDTH;
		config.height = Constants.VIEWPORT_GUI_HEIGHT;
		new LwjglApplication(new EvilBunnyGodGame(), config);
	}
}
