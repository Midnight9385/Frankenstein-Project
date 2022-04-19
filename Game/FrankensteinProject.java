package Game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.net.http.HttpClient.Version;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import GameObjects.*;
import Physics.*;
import sounds.*;
import sounds.SoundPlayer.Volume;
import GameObjects.GameObjectBase.Direction;

public class FrankensteinProject extends JFrame {

    DrawPanel drawPanel = new DrawPanel();

    // player
    public Player player = new Player(Constants.PLAYER_ONE_START_X, Constants.PLAYER_ONE_START_Y, 25, 15);
    JLabel playerLabel = new JLabel(new ImageIcon("icons/PlayerRight.PNG"));

    // ground
    public Floor ground = new Floor(0, 390, 800, 10, Color.GREEN, false);

    // object lists
    public List<ArrayList<Floor>> floors = new ArrayList<ArrayList<Floor>>();
    public List<ArrayList<Wall>> walls = new ArrayList<ArrayList<Wall>>();
    public List<ArrayList<Enemy>> spikes = new ArrayList<ArrayList<Enemy>>();

    // level 1 objects
    public List<Floor> floorsLvlOne = new ArrayList<Floor>();
    public List<Wall> wallsLvlOne = new ArrayList<Wall>();
    public List<Enemy> eneimesLvlOne = new ArrayList<Enemy>();
    // level 2 objects
    public List<Floor> floorsLvlTwo = new ArrayList<Floor>();
    public List<Wall> wallsLvlTwo = new ArrayList<Wall>();
    public List<Enemy> enemiesLvlTwo = new ArrayList<Enemy>();
    // level 3 objects
    public List<Floor> f3 = new ArrayList<Floor>();
    public List<Wall> w3 = new ArrayList<Wall>();
    public List<Enemy> e3 = new ArrayList<Enemy>();
    // level 4 objects
    public List<Floor> f4 = new ArrayList<Floor>();
    public List<Wall> w4 = new ArrayList<Wall>();
    public List<Enemy> e4 = new ArrayList<Enemy>();

    // misc
    PhysicsEngine pE = new PhysicsEngine();
    DoubleJumpTimer timer;
    LevelTimer levelTimer = new LevelTimer(this);

    // images
    ImageIcon notes[] = {new ImageIcon("icons/firstnote.png"),
                         new ImageIcon("icons/lvl1.png"),
                         new ImageIcon("icons/lvl2.png"),
                         new ImageIcon("icons/lvl3.png"),
                         new ImageIcon("icons/lvl4.png"),
                         new ImageIcon("icons/lvl5.png")
                        };
    ImageIcon scrollImage = new ImageIcon("icons/scroll.png");

    Image backgroundImage;

    // background
    JLabel background = new JLabel(new ImageIcon("background.png"));

    JLabel doubleJ = new JLabel("0");
    JLabel levelLabel = new JLabel("0");

    // buttons
    JButton leaveNote = new JButton(new ImageIcon("icons/next.png"));
    JButton mute = new JButton("mute");
    JButton play = new JButton("play music");

    // pick up items
    JLabel note = new JLabel(notes[0]);
    JLabel scroll = new JLabel(scrollImage);

    // object and player dectection
    public boolean jump = false;
    public boolean justJumped = false;
    public boolean doubleJump = false;
    public boolean onScroll = false;
    public boolean doubleJumpTiming = false;
    private int[][] scrollCoords = new int[4][2];
    // misc
    public int startX[] = {Constants.PLAYER_ONE_START_X,Constants.PLAYER_TWO_START_X};
    public int startY[] = {Constants.PLAYER_ONE_START_Y,Constants.PLAYER_TWO_START_Y};
    public int jumpTarget;
    public int level = 0;
    private boolean firstTime = false;
    Random ran = new Random((long)1);

    // constants just to lazy to put them in there
    public final int movementSpeed = 5;
    public final int jumpSpeed = 1;
    public final int gravitySpeed = 1;
    public final int floorLevel = 390;
    public final int jumpHieght = 100;

    public FrankensteinProject() {

        try {
            backgroundImage = ImageIO.read(new File("background.png"));
        } catch (Exception e) {
        }
        ;

        // walls.add(new Wall(0, 390, 800, 10, Color.GREEN));
        playerLabel.setBounds(player.getX(), player.getY(), player.getwidth(), player.getheight());
        // level 1 objects
        floorsLvlOne.add(new Floor(0, 240, 49, 10, Color.BLACK, false));
        floorsLvlOne.add(new Floor(100, 240, 50, 10, Color.BLACK, true));
        wallsLvlOne.add(new Wall(100, floorLevel - 20, 10, 20));
        eneimesLvlOne.add(new Enemy(200, 380, 10, 10));
        scrollCoords[0][0] = Constants.LEVEL_ONE_SCROLL_X;
        scrollCoords[0][1] = Constants.LEVEL_ONE_SCROLL_Y;
        // level 2 objects
        floorsLvlTwo.add(new Floor(0, 330, 758, 10, Color.BLACK, false));
        floorsLvlTwo.add(new Floor(42, 270, 758, 10, Color.black, false));
        floorsLvlTwo.add(new Floor(0, 210, 758, 10, Color.black, false));
        floorsLvlTwo.add(new Floor(42, 160, 758, 10, Color.black, false));
        scrollCoords[1][0] = Constants.LEVEL_TWO_SCROLL_X;
        scrollCoords[1][1] = Constants.LEVEL_TWO_SCROLL_Y;
        // level 3 objects
        f3.add(randomFloor());
        f3.add(randomFloor());
        w3.add(randomWall());
        e3.add(randomEnemy(f3.get(1)));
        // level list in order
        floors.add((ArrayList<Floor>) floorsLvlOne);
        walls.add((ArrayList<Wall>) wallsLvlOne);
        spikes.add((ArrayList<Enemy>) eneimesLvlOne);

        floors.add((ArrayList<Floor>) floorsLvlTwo);
        walls.add((ArrayList<Wall>) wallsLvlTwo);
        spikes.add((ArrayList<Enemy>) enemiesLvlTwo);

        floors.add((ArrayList<Floor>) f3);
        walls.add((ArrayList<Wall>) w3);
        spikes.add((ArrayList<Enemy>) e3);

        // SoundPlayer background = new SoundPlayer("dark-forest");
        // background.playSound();

        leaveNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onScroll = false;
                level++;
                firstTime = true;
              if(firstTime) {
                switch(level){
                  case 1:
                    firstTime = false;
                    player.setX(startX[level]);
                    player.setY(startY[level]); break;
                  case 2:
                    firstTime = false;
                    player.setX(startX[level]);
                    player.setY(startY[level]); break;
                  case 3:
                    firstTime = false;
                    player.setX(startX[level]);
                    player.setY(startY[level]);
                }
            }
            }
        });

        // mute.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         background.volume = Volume.MUTE;
        //         background.play();
        //         drawPanel.repaint();
        //     }
        // });

        // play.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         background.volume = Volume.ON;
        //         background.play();
        //         drawPanel.repaint();
        //     }
        // });

        Action leftAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                boolean check = false;
                for (int z = 0; z < walls.get(level).size(); z++) {
                    if (walls.get(level).get(z).checkX(player, Direction.LEFT)) {
                        check = true;
                        z = walls.get(level).size() + 1;
                    }
                }
                for (int z = 0; z < floors.get(level).size(); z++) {
                    if (floors.get(level).get(z).checkX(player, Direction.LEFT)) {
                        check = true;
                        z = floors.get(level).size() + 1;
                    }
                }
                for (int z = 0; z < spikes.get(level).size(); z++) {
                    if (spikes.get(level).get(z).checkX(player, Direction.LEFT)) {
                        check = true;
                        player.onEnemy = true;
                        z = spikes.get(level).size() + 9;
                    }
                }
                if (!check)
                    player.changeX(-movementSpeed);
                playerLabel.setIcon(new ImageIcon("icons/PlayerLeft.png"));
                drawPanel.repaint();
            }
        };

        InputMap inputMap = drawPanel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = drawPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        actionMap.put("leftAction", leftAction);

        add(drawPanel);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        Action rightAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                boolean check = false;
                for (int z = 0; z < walls.get(level).size(); z++) {
                    if (walls.get(level).get(z).checkX(player, Direction.RIGHT)) {
                        check = true;
                        z = walls.get(level).size() + 9;
                    }
                }
                for (int z = 0; z < floors.get(level).size(); z++) {
                    if (floors.get(level).get(z).checkX(player, Direction.RIGHT)) {
                        check = true;
                        z = floors.get(level).size() + 1;
                    }
                }
                for (int z = 0; z < spikes.get(level).size(); z++) {
                    if (spikes.get(level).get(z).checkX(player, Direction.RIGHT)) {
                        check = true;
                        player.onEnemy = true;
                        z = spikes.get(level).size() + 1;
                    }
                }
                if (!check)
                    player.changeX(movementSpeed);
                playerLabel.setIcon(new ImageIcon("icons/PlayerRight.PNG"));
                drawPanel.repaint();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        actionMap.put("rightAction", rightAction);

        add(drawPanel);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        Action downAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {

                drawPanel.repaint();
            }
        };

        inputMap.put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        actionMap.put("downAction", downAction);

        add(drawPanel);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        Action upAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (!jump && !doubleJump && !justJumped) {
                    jump = true;
                    justJumped = jump;
                    jumpTarget = player.getY() - jumpHieght;
                    startTimer();
                } else if (!doubleJump && !doubleJumpTiming) {
                    jump = true;
                    doubleJump = true;
                    jumpTarget = player.getY() - jumpHieght;
                }
            }
        };

        inputMap.put(KeyStroke.getKeyStroke("UP"), "upAction");
        actionMap.put("upAction", upAction);

        add(drawPanel);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class DrawPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            this.removeAll();

            g.drawImage(backgroundImage, 0, 0, null);
            // this.setBackground(bg);
            scroll.setBounds(scrollCoords[level][0], scrollCoords[level][1], 38, 50);
            this.add(scroll);
            // this.add(note);
            playerLabel.setBounds(player.getX(), player.getY(), player.getwidth(), player.getheight());
            this.add(playerLabel);
            // this.add(mute);
            // this.add(play);
            if (onScroll) {
                note.setIcon(notes[level]);
                leaveNote.setBounds(419, 290, 50, 63);
                this.add(leaveNote);
                scroll.setBounds(-900, -900, 0, 0);
                note.setBounds(250, 25, 289, 365);
                this.add(note);
            } else {
                this.remove(note);
                this.remove(leaveNote);
            }
            // doubleJ.setText(level + "");
            doubleJ.setBounds(0, 0, 800, 200);
            doubleJ.setForeground(Color.WHITE);

            levelLabel.setBounds(0, 0, 400, 100);
            levelLabel.setText(Integer.toString(level+1));
            levelLabel.setForeground(Color.WHITE);
            this.add(levelLabel);

            // doubleJ.setText(doubleJ.getText() + " " + new Boolean(doubleJump).toString()
            // + " "
            // + new Boolean(jump).toString() + " "
            // + new Boolean(justJumped).toString());
            this.add(doubleJ);
            //this.add(background);
            drawAll(g);
            if (scroll.getX() < player.getX() + player.getwidth() && player.getX() < scroll.getX() + scroll.getWidth()
                    && scroll.getY() < player.getY() + player.getheight()
                    && player.getY() < scroll.getY() + scroll.getHeight())
                onScroll = true;
            physics();

            try {
                if (jump)
                    Thread.sleep((long) 2.5);
                else
                    Thread.sleep((long) 5.5);
            } catch (Exception e) {
            }
            repaint();

        }

        public Dimension getPreferredSize() {
            return new Dimension(800, 400);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrankensteinProject();
            }
        });
    }

    public void drawAll(Graphics g) {
        // drawPlayer(player, g);
        drawFloor(ground, g);
        for (int z = 0; z < floors.get(level).size(); z++) {
            drawFloor(floors.get(level).get(z), g);
        }
        for (int z = 0; z < walls.get(level).size(); z++) {
            drawWall(walls.get(level).get(z), g);
        }
        for (int z = 0; z < spikes.get(level).size(); z++) {
            drawEnemy(spikes.get(level).get(z), g);
        }
    }

    public void drawPlayer(Player player, Graphics g) {
        g.setColor(player.getColor());
        g.fillRect(player.getX(), player.getY(), player.getwidth(), player.getheight());
    }

    public void drawWall(Wall ground, Graphics g) {
        g.setColor(ground.getColor());
        g.fillRect(ground.getX(), ground.getY(), ground.getwidth(), ground.getheight());
    }

    public void drawFloor(Floor ground, Graphics g) {
        g.setColor(ground.getColor());
        g.fillRect(ground.getX(), ground.getY(), ground.getwidth(), ground.getheight());
    }

    public void drawEnemy(Enemy ground, Graphics g) {
        int[] x = { ground.getX(), ground.getX() + (ground.getwidth() / 2), ground.getX() + ground.getwidth() };
        int[] y = { ground.getY() + ground.getheight(), ground.getY(), ground.getY() + ground.getheight() };
        g.setColor(ground.getColor());
        g.fillPolygon(x, y, 3);
    }

    public void physics() {
        pE.physics(this);
    }

    public void startTimer() {
        timer = new DoubleJumpTimer(0.75, this);
    }

    public Floor randomFloor(){
      int h = (ran.nextInt(40))+10;
      int w = (ran.nextInt(40))+10;
      int x = ran.nextInt(800);
      int y = ran.nextInt(150)+250;
      while(h%5!=0){
        h = (ran.nextInt(40))+10;
      }
      while(w%5!=0){
        w = (ran.nextInt(40))+10;
      }
      while(x%5!=0){
        x = ran.nextInt(800);
      }
      
      return new Floor(x,y,w,h,Color.BLACK,false);
    }
    public Wall randomWall(){
      int h = (ran.nextInt(40))+10;
      int w = (ran.nextInt(40))+10;
      int x = ran.nextInt(800);
      int y = ran.nextInt(150)+250;
      while(h%5!=0){
        h = (ran.nextInt(40))+10;
      }
      while(w%5!=0){
        w = (ran.nextInt(40))+10;
      }
      while(x%5!=0){
        x = ran.nextInt(800);
      }
      return new Wall(x,y,w,h,Color.BLACK,false);
    }
    public Enemy randomEnemy(Floor f){
      return new Enemy((ran.nextInt(f.getwidth())+f.getX()),f.getY()+10,10,10);
    }
}
