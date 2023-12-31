package bodies.characters.Seija;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import bodies.Direction;
import bodies.characters.AbstractCharacter;
import bodies.characters.AbstractPlayer;
import bodies.characters.Misc.spellActions.BasicDashFactory;
import bodies.characters.Misc.spellActions.BasicMeleeFactory;
import bodies.characters.Sakuya.spellCards.TestSpellCard;
import bodies.characters.Seija.spellActions.GravityReversalFactory;

public class Seija extends AbstractCharacter {
    private static String[] imagePaths = new String[]{
        "assets/images/sprites/seija.png",
        "assets/images/sprites/cut ins.png"
    };
    private static BufferedImage[] sprites = new BufferedImage[5];
    private static boolean isSetup = false;
    private static BufferedImage spriteSheet;
    private static BufferedImage cutIn;

    public Seija(AbstractPlayer player) {
        super(player, "Seija");
        setupSpellActions();
        setupSpellCards();

        setupCharacter();
    }

    //TODO Sakuya spell cards
    @Override
    protected void setupSpellCards() {
        for (int i = 0; i < deck.length; i++) {
            deck[i] = new TestSpellCard(getPlayer());
        }
    }

    //TODO Sakuya spell actions
    @Override
    protected void setupSpellActions() {
        for (int i = 0; i < 21; i++) {
            comboMapping.get(i).snd = new GravityReversalFactory(getPlayer());
        }

        int i = 21;
        for (Direction dir: Direction.values()) {
            comboMapping.get(i).snd = new BasicDashFactory(getPlayer(), dir);
            i++;
        }
    }

    public static void setupCharacter() {
        if (!Seija.isSetup) {
            try {
                Seija.setUpSprites();
                Seija.isSetup = true;
            } catch (IOException e) {
                System.out.println("failed to get sprites");
            }
        }
    }

    public static void setUpSprites() throws IOException {
        spriteSheet = ImageIO.read(new File(imagePaths[0]));
        int width = spriteSheet.getWidth() / 5;
        int height = spriteSheet.getHeight() / 1;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 1; y++) {
                sprites[x + y * 3] = spriteSheet.getSubimage(x * width, y * height, width, height);
            }
        }
        cutIn = ImageIO.read(new File(imagePaths[1]));
    }

    public static BufferedImage[] getSprites() {
        return sprites;
    }

    public static BufferedImage getCutIn() {
        return cutIn;
    }
}
