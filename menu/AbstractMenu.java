package menu;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Game;

public abstract class AbstractMenu {
    
    protected Integer selectedIndex = null;
    protected AbstractOption[] options = {};
    protected Game game;
    private String menuName;
    private AbstractMenu prevMenu;
    private BufferedImage background;

    public AbstractMenu(Game game, String menuName, AbstractMenu prevMenu) {
        this.game = game;
        this.selectedIndex = 0;
        this.menuName = menuName;
        this.prevMenu = prevMenu;
    }

    
    public AbstractMenu(Game game, String menuName, AbstractMenu prevMenu, String imagePath) {
        this.game = game;
        this.selectedIndex = 0;
        this.menuName = menuName;
        this.prevMenu = prevMenu;

        try {
            setBackground(ImageIO.read(new File(imagePath)));
        } catch (IOException e) {
            System.err.println("failed to get image");
        }
    }

    public abstract void animateSelectionChange();

    public void incrementSelection(int n) {
        if (selectedIndex == null || options.length == 0) throw new NullPointerException();
        selectedIndex = (selectedIndex + n + options.length) % options.length;
        // System.out.println(selectedIndex);
    }

    public void decrementSelection(int n) {
        incrementSelection(-n);
    }

    public AbstractOption[] getOptions() {
        return this.options;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public AbstractMenu getPrevMenu() {
        return prevMenu;
    }

    public Integer getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(Integer selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public void runSelected() {
        options[selectedIndex].statefulHandler();
        game.notifyObservers();
    }

    public AbstractOption getSelected() {
        return options[selectedIndex];
    }

    public BufferedImage getBackground() {
        return background;
    }

    public void setBackground(BufferedImage background) {
        this.background = background;
    }
}
