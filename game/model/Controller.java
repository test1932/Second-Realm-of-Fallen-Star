// package
package game.model;

// standard library imports
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

// local imports
import game.Game;

/**
 * @author: ~Yukarin~ <3
 * Class used for running game loop and processing changes.
 */
public class Controller {
    /**
     * class for representing a pair of two generic elements of arbitrary types.
     * similar to the pair type in Haskell.
     */
    public class Pair <U,V> {
        public U fst;
        public V snd;

        /**
         * constructor method.
         * @param val1 element 1 of the pair.
         * @param val2 element 2 of the pair.
         */
        public Pair (U val1, V val2) {
            fst = val1;
            snd = val2;
        }

        /**
         * overridden toString method used for debugging.
         */
        @Override
        public String toString() {
            return String.valueOf(snd);
        }
    }

    // constants
    private final Long RECENT_TIMEOUT = Long.valueOf(1000);

    // mutexes and references
    private ReentrantLock recMutex = new ReentrantLock();
    private ReentrantLock holdMutex = new ReentrantLock();
    private updater gameLoop;
    public Battle bat;
    public Game game;

    // key press data structures
    public LinkedList<Integer> heldKeys = new LinkedList<Integer>();
    public List<Pair<Integer, Long>> recentlyRel
        = Collections.synchronizedList(new LinkedList<Pair<Integer, Long>>());

    /**
     * class representing link between game loop updating the model
     * and the key listener for getting input.
     * Constructor method.
     * @param b battle being represented (not necessarily shown).
     * @param g game being represented.
     */
    public Controller (Battle b, Game g) {
        this.bat = b;
        this.game = g;
        gameLoop = new updater(this, recMutex, holdMutex, game);
        gameLoop.start();
    }

    /**
     * returns whether a particular keycode is currently being held down on
     * the keyboard by the user.
     * @param keyid code of the key.
     * @return whether the key is currently being held.
     */
    public Boolean isKeyHeld(Integer keyid) {
        return this.heldKeys.contains(keyid);
    }

    /**
     * adds a key to the list of keys being held.
     * @param keyid key code to add to the list.
     */
    public void holdKey(Integer keyid) {
        if (!isKeyHeld(keyid)) {
            holdMutex.lock();
            this.heldKeys.add(keyid);
            holdMutex.unlock();
        }
    }

    /**
     * remove keycode from the list of hed keys and add it to the list of
     * recently held keys.
     * @param keyid keycode to remove from the list.
     */
    public void releaseKey(Integer keyid) {
        holdMutex.lock();
        this.heldKeys.remove(keyid);
        holdMutex.unlock();
        recMutex.lock();
        this.recentlyRel.add(new Pair<Integer,Long>(keyid, RECENT_TIMEOUT));
        recMutex.unlock();
    }
}
