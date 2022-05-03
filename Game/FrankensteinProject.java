package Game;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import GameObjects.*;
import Physics.*;
import GameObjects.GameObjectBase.Direction;

public class FrankensteinProject extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 6537893818175455795L;

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
    //lvl 5
    public List<Floor> f5 = new ArrayList<Floor>();
    public List<Wall> w5 = new ArrayList<Wall>();
    public List<Enemy> e5 = new ArrayList<Enemy>();
    //lvl6
    public List<Floor> f6 = new ArrayList<Floor>();
    public List<Wall> w6 = new ArrayList<Wall>();
    public List<Enemy> e6 = new ArrayList<Enemy>();
    //lvl7
    public List<Floor> f7 = new ArrayList<Floor>();
    public List<Wall> w7 = new ArrayList<Wall>();
    public List<Enemy> e7 = new ArrayList<Enemy>();
    //last lvl
    public List<Floor> f8 = new ArrayList<Floor>();
    public List<Wall> w8 = new ArrayList<Wall>();
    public List<Enemy> e8 = new ArrayList<Enemy>();

    // misc
    PhysicsEngine pE = new PhysicsEngine();
    DoubleJumpTimer timer;
    LevelTimer levelTimer = new LevelTimer(this);

    // images
    ImageIcon notes[] = { new ImageIcon("icons/firstnote.png"),
            new ImageIcon("icons/lvl2.png"),
            new ImageIcon("icons/lvl3.png"),
            new ImageIcon("icons/lvl4.png"),
            new ImageIcon("icons/lvl5.png"),
            new ImageIcon("icons/lvl6.png"),
            new ImageIcon("icons/lvl7.png"),
            new ImageIcon("icons/conclusion.png")             
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
    private int[][] scrollCoords = new int[8][2];
    // misc
    public int startX[] = { Constants.PLAYER_ONE_START_X, Constants.PLAYER_TWO_START_X, 0, 0,0,0,0,0,0,0,0,0 };
    public int startY[] = { Constants.PLAYER_ONE_START_Y, Constants.PLAYER_TWO_START_Y, 365, 365, 365, 365, 365, 365, 365, 365, 365, 365 };
    public int jumpTarget;
    public int level = 0;
    long seed = /* Long.valueOf(1650898065512); */System.currentTimeMillis();
    Random ran = new Random(seed);
    int scrollChoice;
    boolean lastLevel = false;

    // constants just to lazy to put them in there
    public final int movementSpeed = 5;
    public final int jumpSpeed = 1;
    public final int gravitySpeed = 1;
    public final int floorLevel = 390;
    public final int jumpHieght = 100;
    public final int max_Level = 9;

    public FrankensteinProject() {

        try {
            backgroundImage = ImageIO.read(new File("background.png"));
        } catch (Exception e) {
        }
        ;

        // walls.add(new Wall(0, 390, 800, 10, Color.GREEN));
        playerLabel.setBounds(player.getX(), player.getY(), player.getwidth(), player.getheight());
        // level 1 objects
        // floorsLvlOne.add(new Floor(0, 240, 49, 10, Color.BLACK, false));
        // floorsLvlOne.add(new Floor(100, 240, 50, 10, Color.BLACK, true));
        wallsLvlOne.add(new Wall(100, floorLevel - 20, 10, 20));
        eneimesLvlOne.add(new Enemy(200, 380, 10, 10));
        // scrollCoords[0][0] = Constants.LEVEL_ONE_SCROLL_X;
        // scrollCoords[0][1] = Constants.LEVEL_ONE_SCROLL_Y;
        for (int i = ran.nextInt(10); i <= 15; i++) {
            floorsLvlOne.add(randomFloor());
            wallsLvlOne.add(randomWall());
        }
        for (int i = ran.nextInt(floorsLvlOne.size()); i <= floorsLvlOne.size() + ran.nextInt(5); i++) {
            int floorChoice = ran.nextInt(floorsLvlOne.size());
            while (floorsLvlOne.get(floorChoice).getSpikeCount() >= floorsLvlOne.get(floorChoice).getMaxSpikes()) {
                floorChoice = ran.nextInt(floorsLvlOne.size());
            }
            eneimesLvlOne.add(randomEnemy(floorsLvlOne.get(floorChoice)));
        }

        // level 2 objects
        // floorsLvlTwo.add(new Floor(0, 330, 758, 10, Color.BLACK, false));
        // floorsLvlTwo.add(new Floor(42, 270, 758, 10, Color.black, false));
        // floorsLvlTwo.add(new Floor(0, 210, 758, 10, Color.black, false));
        // floorsLvlTwo.add(new Floor(42, 160, 758, 10, Color.black, false));

        for (int i = ran.nextInt(10); i <= 15; i++) {
            floorsLvlTwo.add(randomFloor());
            wallsLvlTwo.add(randomWall());
        }
        for (int i = ran.nextInt(floorsLvlTwo.size()); i <= floorsLvlTwo.size() + ran.nextInt(5); i++) {
            int floorChoice = ran.nextInt(floorsLvlTwo.size());
            while (floorsLvlTwo.get(floorChoice).getSpikeCount() >= floorsLvlTwo.get(floorChoice).getMaxSpikes()) {
                floorChoice = ran.nextInt(floorsLvlTwo.size());
            }
            enemiesLvlTwo.add(randomEnemy(floorsLvlTwo.get(floorChoice)));
        }

        // level 3 objects
        for (int i = ran.nextInt(10); i <= 15; i++) {
            f3.add(randomFloor());
            w3.add(randomWall());
        }
        for (int i = ran.nextInt(f3.size()); i <= f3.size() + ran.nextInt(5); i++) {
            int floorChoice = ran.nextInt(f3.size());
            while (f3.get(floorChoice).getSpikeCount() >= f3.get(floorChoice).getMaxSpikes()) {
                floorChoice = ran.nextInt(f3.size());
            }
            e3.add(randomEnemy(f3.get(floorChoice)));
        }
        // level 4 objects
        for (int i = ran.nextInt(10); i <= 15; i++) {
            f4.add(randomFloor());
            w4.add(randomWall());
        }
        for (int i = ran.nextInt(f4.size()); i <= f4.size() + ran.nextInt(5); i++) {
            int floorChoice = ran.nextInt(f4.size());
            while (f4.get(floorChoice).getSpikeCount() >= f4.get(floorChoice).getMaxSpikes()) {
                floorChoice = ran.nextInt(f4.size());
            }
            e4.add(randomEnemy(f4.get(floorChoice)));
        }
      //lvl 5
      for (int i = ran.nextInt(10); i <= 15; i++) {
            f5.add(randomFloor());
            w5.add(randomWall());
        }
        for (int i = ran.nextInt(f5.size()); i <= f5.size() + ran.nextInt(5); i++) {
            int floorChoice = ran.nextInt(f5.size());
            while (f5.get(floorChoice).getSpikeCount() >= f5.get(floorChoice).getMaxSpikes()) {
                floorChoice = ran.nextInt(f5.size());
            }
            e5.add(randomEnemy(f5.get(floorChoice)));
        }
      //lvl6
      for (int i = ran.nextInt(10); i <= 15; i++) {
            f6.add(randomFloor());
            w6.add(randomWall());
        }
        for (int i = ran.nextInt(f6.size()); i <= f6.size() + ran.nextInt(5); i++) {
            int floorChoice = ran.nextInt(f6.size());
            while (f6.get(floorChoice).getSpikeCount() >= f6.get(floorChoice).getMaxSpikes()) {
                floorChoice = ran.nextInt(f6.size());
            }
            e6.add(randomEnemy(f6.get(floorChoice)));
        }
      //lvl7
      for (int i = ran.nextInt(10); i <= 15; i++) {
            f7.add(randomFloor());
            w7.add(randomWall());
        }
        for (int i = ran.nextInt(f7.size()); i <= f7.size() + ran.nextInt(5); i++) {
            int floorChoice = ran.nextInt(f7.size());
            while (f7.get(floorChoice).getSpikeCount() >= f7.get(floorChoice).getMaxSpikes()) {
                floorChoice = ran.nextInt(f7.size());
            }
            e7.add(randomEnemy(f7.get(floorChoice)));
        }
      //lvl8
      for (int i = ran.nextInt(10); i <= 15; i++) {
            f8.add(randomFloor());
            w8.add(randomWall());
        }
        for (int i = ran.nextInt(f8.size()); i <= f8.size() + ran.nextInt(5); i++) {
            int floorChoice = ran.nextInt(f8.size());
            while (f8.get(floorChoice).getSpikeCount() >= f8.get(floorChoice).getMaxSpikes()) {
                floorChoice = ran.nextInt(f8.size());
            }
            e8.add(randomEnemy(f8.get(floorChoice)));
        }
        scrollCoords[0][0] = getSuitableScrollX(floorsLvlOne);
        scrollCoords[0][1] = getSuitableScrollY(scrollChoice, floorsLvlOne);
        scrollCoords[1][0] = getSuitableScrollX(floorsLvlTwo);
        scrollCoords[1][1] = getSuitableScrollY(scrollChoice, floorsLvlTwo);
        scrollCoords[2][0] = getSuitableScrollX(f3);
        scrollCoords[2][1] = getSuitableScrollY(scrollChoice, f3);
        scrollCoords[3][0] = getSuitableScrollX(f4);
        scrollCoords[3][1] = getSuitableScrollY(scrollChoice, f4);
        scrollCoords[4][0] = getSuitableScrollX(f5);
        scrollCoords[4][1] = getSuitableScrollY(scrollChoice, f5);
        scrollCoords[5][0] = getSuitableScrollX(f6);
        scrollCoords[5][1] = getSuitableScrollY(scrollChoice, f6);
        scrollCoords[6][0] = getSuitableScrollX(f7);
        scrollCoords[6][1] = getSuitableScrollY(scrollChoice, f7);
        scrollCoords[7][0] = getSuitableScrollX(f8);
        scrollCoords[7][1] = getSuitableScrollY(scrollChoice, f8);
        // f3.add(randomFloor());
        // w3.add(randomWall());
        // e3.add(randomEnemy(f3.get(1)));
        // level list in order
        // floors.add((ArrayList<Floor>) f3);
        // walls.add((ArrayList<Wall>) w3);
        // spikes.add((ArrayList<Enemy>) e3);

        // floors.add((ArrayList<Floor>) f4);
        // walls.add((ArrayList<Wall>) w4);
        // spikes.add((ArrayList<Enemy>) e4);

        floors.add((ArrayList<Floor>) floorsLvlOne);
        walls.add((ArrayList<Wall>) wallsLvlOne);
        spikes.add((ArrayList<Enemy>) eneimesLvlOne);

        floors.add((ArrayList<Floor>) floorsLvlTwo);
        walls.add((ArrayList<Wall>) wallsLvlTwo);
        spikes.add((ArrayList<Enemy>) enemiesLvlTwo);

        floors.add((ArrayList<Floor>) f3);
        walls.add((ArrayList<Wall>) w3);
        spikes.add((ArrayList<Enemy>) e3);

        floors.add((ArrayList<Floor>) f4);
        walls.add((ArrayList<Wall>) w4);
        spikes.add((ArrayList<Enemy>) e4);

      floors.add((ArrayList<Floor>) f5);
        walls.add((ArrayList<Wall>) w5);
        spikes.add((ArrayList<Enemy>) e5);

      floors.add((ArrayList<Floor>) f6);
        walls.add((ArrayList<Wall>) w6);
        spikes.add((ArrayList<Enemy>) e6);

      floors.add((ArrayList<Floor>) f7);
        walls.add((ArrayList<Wall>) w7);
        spikes.add((ArrayList<Enemy>) e7);

      floors.add((ArrayList<Floor>) f8);
        walls.add((ArrayList<Wall>) w8);
        spikes.add((ArrayList<Enemy>) e8);

        // SoundPlayer background = new SoundPlayer("dark-forest");
        // background.playSound();

        leaveNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onScroll = false;
                if(level == 7){
                     lastLevel = true;;
                }else{
                level++;
                player.setX(startX[level]);
                player.setY(startY[level]);
                }
            }
        });

        // mute.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent e) {
        // background.volume = Volume.MUTE;
        // background.play();
        // drawPanel.repaint();
        // }
        // });

        // play.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent e) {
        // background.volume = Volume.ON;
        // background.play();
        // drawPanel.repaint();
        // }
        // });

        Action leftAction = new AbstractAction() {
            /**
             *
             */
            private static final long serialVersionUID = -2576054438128869082L;

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
            /**
             *
             */
            private static final long serialVersionUID = -7650377710184326334L;

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
            /**
             *
             */
            private static final long serialVersionUID = 5887534192360011425L;

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
            /**
             *
             */
            private static final long serialVersionUID = 8495455407047316652L;

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
        /**
         *
         */
        private static final long serialVersionUID = -3530164898710369207L;

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            this.removeAll();

            g.drawImage(backgroundImage, 0, 0, null);
            if(lastLevel){
                doubleJ.setText("You have made it through Frankenstein's monsters journey. Thank you for going on this quest!");
                Font fnt2 = new Font("Calibri",Font.BOLD,16);
                doubleJ.setBounds(50,100,700,100);
                doubleJ.setForeground(Color.WHITE);
                doubleJ.setFont(fnt2);
                this.add(doubleJ);
            }else{
                
            // this.setBackground(bg);
            scroll.setBounds(scrollCoords[level][0], scrollCoords[level][1], 38, 36);
            scroll.setBackground(Color.WHITE);
            this.add(scroll);
            // this.add(note);
            playerLabel.setBounds(player.getX(), player.getY(), player.getwidth(), player.getheight());
            this.add(playerLabel);
            // this.add(mute);
            // this.add(play);
            if (onScroll) {
                note.setIcon(notes[level]);
                leaveNote.setBounds(419, 290, 50, 63);
                if (level == 3) {
                    leaveNote.setBounds(419, 310, 50, 63);
                }
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
            levelLabel.setText(Integer.toString(level + 1) + "  " + Long.toString(seed));
            levelLabel.setForeground(Color.WHITE);
            this.add(levelLabel);

            // doubleJ.setText(doubleJ.getText() + " " + new Boolean(doubleJump).toString()
            // + " "
            // + new Boolean(jump).toString() + " "
            // + new Boolean(justJumped).toString());
            this.add(doubleJ);
            // this.add(background);
            drawAll(g);
            if (scroll.getX() < player.getX() + player.getwidth() && player.getX() < scroll.getX() + scroll.getWidth()
                    && scroll.getY() < player.getY() + player.getheight()
                    && player.getY() < scroll.getY() + scroll.getHeight())
                onScroll = true;
            physics();
        }
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

    public Floor randomFloor() {
        int h = 10;
        int w = (ran.nextInt(40)) + 10;
        int x = ran.nextInt(710) + 90;
        int y = ran.nextInt(140) + 250;
        while (w % 5 != 0) {
            w = (ran.nextInt(40)) + 10;
        }
        while (x % 5 != 0) {
            x = ran.nextInt(800);
        }

        return new Floor(x, y, w, h, Color.BLACK, false);
    }

    public Wall randomWall() {
        int h = (ran.nextInt(40)) + 10;
        int w = 10;
        int x = ran.nextInt(710) + 90;
        int y = ran.nextInt(140) + 250;
        while (h % 5 != 0) {
            h = (ran.nextInt(40)) + 10;
        }
        while (x % 5 != 0) {
            x = ran.nextInt(800);
        }
        return new Wall(x, y, w, h, Color.BLACK, false);
    }

    public Enemy randomEnemy(Floor f) {
        int width = (f.getwidth() - 10);
        f.setSpikeCount(f.getSpikeCount() + 1);
        if (width <= 0)
            return new Enemy(f.getX(), f.getY() - 10, 10, 10);
        return new Enemy((ran.nextInt(width)) + f.getX(), f.getY() - 10, 10, 10);
    }

    public int getSuitableScrollX(List<Floor> f3) {
        scrollChoice = ran.nextInt(f3.size() - 1);
        while (f3.get(scrollChoice).getSpikeCount() != 0) {
            scrollChoice = ran.nextInt(f3.size() - 1);
        }
        return f3.get(scrollChoice).getX() - Math.abs(((38 - f3.get(scrollChoice).getwidth()) / 2));
    }

    public int getSuitableScrollY(int i, List<Floor> f3) {
        return f3.get(i).getY() - (36);
    }
}