package todolist;

import java.awt.Color;
import java.awt.Font;

public class Theme {
    // Colors
    public static final Color BG_PRIMARY = new Color(238, 238, 238);
    public static final Color BG_SECONDARY = new Color(34, 40, 49);
    public static final Color BG_PANEL = new Color(57, 62, 70);

    public static final Color TEXT_PRIMARY = new Color(24, 28, 36);
    public static final Color TEXT_SECONDARY = new Color(180, 180, 180);

    public static final Color ACCENT = new Color(0, 173, 181);
    public static final Color SUCCESS = new Color(76, 175, 80);
    public static final Color WARNING = new Color(255, 193, 7);
    public static final Color DANGER = new Color(244, 67, 54);

    // Fonts
    public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
    public static final Font NORMAL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 12);

    /**
     * Prevents creating Theme objects because this class only stores constants.
     */
    private Theme() {
    }
}
