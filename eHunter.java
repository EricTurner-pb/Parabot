package hunter;

import org.rev317.api.events.MessageEvent;
import org.rev317.api.events.listeners.MessageListener;
import org.rev317.api.methods.Interfaces;
import org.rev317.api.methods.Skill;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Mouse;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.api.methods.*;
import org.rev317.api.wrappers.interactive.Npc;
import org.rev317.api.wrappers.scene.Area;
import org.rev317.api.wrappers.scene.SceneObject;
import org.rev317.api.wrappers.scene.Tile;
import org.rev317.api.wrappers.walking.TilePath;
 


import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
 


import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
@ScriptManifest(author = "EricTurner", category = Category.OTHER, description = "Catches and Banks Implings and Butterflys", name = "eHunter", servers = { "Near Reality" }, version = 1)
public final class eHunter extends Script implements Paintable, MessageListener {
 
        private static ArrayList<Strategy> strategies = new ArrayList<Strategy>();
        public static Area hunter = new Area(new Tile(2138, 5106, 0), new Tile(
                        2164, 5106, 0), new Tile(2164, 5091, 0), new Tile(2138, 5091, 0));
        private final Tile[] Edge = { new Tile(3094, 3494, 0) };
        private final Tile[] Fally = { new Tile(3012, 3355, 0) };
        private int startXP = 0;
        private int startLVL = 0;
        private long START_TIME;
        public static String INFO;
        public long Trips = 0;
        public int lvl = Skill.CONSTRUCTION.getLevel();
        public int impId = 0;
        public int flyId = 0;
        public static String name = "";
        public int[] guiId = {1, 2};
        public int Price = 0;
        public int ImpsCaught = 0;
        Gui g = new Gui();
        public boolean guiWait = true;
 
        @Override
        public boolean onExecute() {
                strategies.add(new LogOut());
                strategies.add(new Catch());
                strategies.add(new Shop());
                strategies.add(new Banking());
                provide(strategies);
                startXP = Skill.CONSTRUCTION.getExperience();
                startLVL = Skill.CONSTRUCTION.getLevel();
                START_TIME = System.currentTimeMillis();
                g.setVisible(true);
                while (guiWait == true) {
                        sleep(500);
 
                }
                return true;
 
        }
 
        public void onFinish() {
 
        }
 
        public class LogOut implements Strategy {
 
                @Override
                public boolean activate() {
                        return !Game.isLoggedIn();
                }
 
                @Override
                public void execute() {
                        if (!Game.isLoggedIn()) {
                                INFO = "Logging In";
                                Point loginButton = new Point(368, 314);
                                Mouse.getInstance().click(loginButton);
                                return;
                        }
                }
        }
 
        public class Gui extends JFrame {
 
                /**
                 *
                 */
                private static final long serialVersionUID = 4491442541403176713L;
                private JPanel contentPane;
 
                /**
                 * Launch the application.
                 */
                public void main(String[] args) {
                        EventQueue.invokeLater(new Runnable() {
                                public void run() {
                                        try {
                                                Gui frame = new Gui();
                                                frame.setVisible(true);
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                }
                        });
                }
 
                @SuppressWarnings("unchecked")
                public Gui() {
                        initComponents();
                        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        setBounds(300, 300, 400, 400);
                        contentPane = new JPanel();
                        contentPane.setBorder(new EmptyBorder(30, 30, 30, 30));
                        setContentPane(contentPane);
                        contentPane.setLayout(null);
 
                        eHunter = new JLabel("eHunter");
                        eHunter.setFont(new Font("Arial", Font.ITALIC, 22));
                        eHunter.setBounds(280, 280, 200, 100);
                        contentPane.add(eHunter);
 
                        WhatToCatch = new JLabel("What do you want to catch?");
                        WhatToCatch.setBounds(17, 49, 200, 100);
                        contentPane.add(WhatToCatch);
 
                        WhereToBank = new JLabel("How do you want to Hunt?");
                        WhereToBank.setBounds(220, 49, 200, 100);
                        contentPane.add(WhereToBank);
 
                        INFOS = new JLabel("Please take pictures of long Proggies and");
                        INFOS.setBounds(115, 150, 200, 100);
                        contentPane.add(INFOS);
 
                        INFOS1 = new JLabel("post them on the script thread at :");
                        INFOS1.setBounds(115, 170, 200, 100);
                        contentPane.add(INFOS1);
 
                        INFOS2 = new JLabel("INSERT LINK HERE");
                        INFOS2.setBounds(115, 190, 200, 100);
                        contentPane.add(INFOS2);
 
                        NpcToCatch = new JComboBox<Object>();
                        NpcToCatch.setModel(new DefaultComboBoxModel<Object>(
                                        new String[] { "Imps" }));
                        NpcToCatch.setBounds(17, 120, 90, 30);
                        contentPane.add(NpcToCatch);
 
                        BankToUse = new JComboBox<Object>();
                        BankToUse.setModel(new DefaultComboBoxModel<Object>(new String[] {
                                        "Bank(Edgeville)", "Bank(Falador)", "ToCome(C1D1)",
                                        "ToCome(Sell)" }));
                        BankToUse.setBounds(220, 120, 90, 30);
                        contentPane.add(BankToUse);
 
                        btnStart = new JButton("Start");
                        btnStart.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        if (BankToUse.getSelectedItem().equals("Bank(Edgeville)")) {
                                                guiId[2] = 1;
                                        } else if (BankToUse.getSelectedItem().equals(
                                                        "Bank(Falador)")) {
                                        	guiId[2] = 2;
                                        } else if (BankToUse.getSelectedItem().equals(
                                                        "ToCome(C1D1)")) {
                                        	guiId[2] = 1;
                                        } else if (BankToUse.getSelectedItem().equals(
                                                        "ToCome(Sell)")) {
                                        	guiId[2] = 1;
                                        }
 
                                        if (NpcToCatch.getSelectedItem().equals("Imps")) {
                                                guiId[1] = 1;
                                        }
                                        guiWait = false;
                                        g.dispose();
                                }
                        });
                        btnStart.setBounds(130, 260, 89, 23);
                        contentPane.add(btnStart);
                }
 
                private void initComponents() {
                        eHunter = new JLabel();
                        INFOS = new JLabel();
                        INFOS1 = new JLabel();
                        INFOS2 = new JLabel();
                        WhatToCatch = new JLabel();
                        WhereToBank = new JLabel();
                        NpcToCatch = new JComboBox<Object>();
                        BankToUse = new JComboBox<Object>();
 
                }
 
                private JLabel eHunter;
                private JButton btnStart;
                private JComboBox<Object> NpcToCatch;
                private JLabel WhatToCatch;
                private JLabel WhereToBank;
                private JLabel INFOS;
                private JLabel INFOS1;
                private JLabel INFOS2;
                private JComboBox<Object> BankToUse;
        }
 
        @Override
        public void paint(Graphics g) {
                int currentXP = Skill.CONSTRUCTION.getExperience();
                int currentLVL = Skill.CONSTRUCTION.getLevel();
                g.drawImage(paintImage, 0, 330, null);
                g.drawString("INFO: " + INFO, 322, 448);
                g.drawString("Time: " + TimeRunning(START_TIME), 15, 448);
                g.drawString("Xp/hr: " + formatNumb(currentXP - startXP) + "(" + perHr(currentXP - startXP) + ")",
                                158, 448);
                g.drawString("Cash Gained: " + formatNumb(Price), 158,
                              410);
                g.drawString("Level: " + lvl + "(" + (startLVL - currentLVL) + ")",
                                322, 410);
                g.drawString("Imps Caught: " + ImpsCaught, 15, 410);
        }
 
        public class Catch implements Strategy {
 
                @Override
                public boolean activate() {
                        return !Inventory.isFull() && guiId[1] == 1;
                }
 
                @Override
                public void execute() {
                        if (!Inventory.isFull()
                                        && !hunter.contains(Players.getLocal().getLocation())) {
                                INFO = "Teleporting to Hunting Grounds.";
                                Mouse.getInstance().click(new Point(742, 483));
                                Time.sleep(1000);
                                Mouse.getInstance().click(new Point(687, 353));
                                Time.sleep(1000);
                                Mouse.getInstance().click(new Point(259, 383));
                                Time.sleep(1300);
                                Mouse.getInstance().click(new Point(645, 184));
                                Time.sleep(3000);
                                Camera.setPitch(true);
                        }
                        if (lvl < 17) {
                                impId = 6055;
 
                                name = "Baby impling";
                        }
                        if ((lvl >= 17) && (lvl < 20)) {
                                impId = 6056;
 
                                name = "Young impling";
                        }
                        if ((lvl >= 20) && (lvl < 30)) {
                                impId = 6057;
 
                                name = "Gourmet impling";
                        }
                        if ((lvl >= 30) && (lvl < 34)) {
                                flyId = 5085;
 
                                name = "Ruby Harvest";
                        }
                        if ((lvl >= 34) && (lvl < 40)) {
                                impId = 6058;
 
                                name = "Earth impling";
                        }
                        if ((lvl >= 40) && (lvl < 45)) {
                                impId = 6059;
 
                                name = "Essence impling";
                        }
                        if ((lvl >= 45) && (lvl < 50)) {
                                flyId = 5084;
 
                                name = "Sapphire Glacialis";
                        }
                        if ((lvl >= 50) && (lvl < 58)) {
                                impId = 6060;
 
                                name = "Eclectic impling";
                        }
                        if ((lvl >= 58) && (lvl < 65)) {
                                impId = 6061;
 
                                name = "Nature impling";
                        }
                        if ((lvl >= 65) && (lvl < 74)) {
                                impId = 6062;
 
                                name = "Magpie impling";
                        }
                        if ((lvl >= 74) && (lvl < 75)) {
                                impId = 6063;
 
                                name = "Ninja impling";
                        }
                        if ((lvl >= 75) && (lvl < 85)) {
                                flyId = 5083;
 
                                name = "Snowy Knight";
                        }
                        if ((lvl >= 85) && (lvl < 90)) {
                                flyId = 5082;
 
                                name = "Black Warlock";
                        }
                        if (lvl >= 90) {
                                impId = 6064;
 
                                name = "Dragon impling";
                        }
 
                        if (Npcs.getNearest(impId, flyId) != null){
                        	final Npc[] Huntee = Npcs.getNearest(impId, flyId);
                                                if (hunter.contains(Players.getLocal().getLocation()) && hunter != null
                                                                && Players.getLocal().getAnimation() == -1
                                                                && Players.getLocal() != null
                                                                && Huntee[0] != null) {
                                                        if (!Huntee[0].isOnScreen() && !Inventory.isFull()
                                                                        && Huntee[0] != null) {
                                                                INFO = "Walking to " + name + ".";
                                                                Huntee[0].getLocation().clickMM();
                                                                Time.sleep(250);
                                                        }
                                                        while (Huntee[0].isOnScreen()
                                                                        && !Inventory.isFull() && Players.getLocal() != null && Huntee[0] != null) {
                                                                Huntee[0].interact("Catch " + name);
                                                                INFO = "Catching " + name + ".";
                                                                Time.sleep(750);
                                                    }
                                           }
                                 }
                        }
        }
        public class Shop implements Strategy {
 
                @Override
                public boolean activate() {
                        return Inventory.isFull()
                                        && hunter.contains(Players.getLocal().getLocation());
                }
 
                @Override
                public void execute() {
                        if (Inventory.isFull()
                                        && hunter.contains(Players.getLocal().getLocation()) 
                                        && hunter != null) {
                                        if (guiId[2] == 1) {
                                                INFO = "Teleporting to Edgeville Bank.";
                                                Mouse.getInstance().click(new Point(743, 185));
                                                Time.sleep(2000);
                                                Mouse.getInstance().click(new Point(570, 239));
                                                Time.sleep(2000);
                                                Mouse.getInstance().click(new Point(645, 184));
                                                Time.sleep(3500);
                                        } else if (guiId[2] == 2) {
                                                INFO = "Teleporting to Falador Bank.";
                                                Mouse.getInstance().click(new Point(741, 482));
                                                Time.sleep(2000);
                                                Mouse.getInstance().click(new Point(596, 419));
                                                Time.sleep(3500);
                                                Mouse.getInstance().click(new Point(645, 184)); 
                                }
                        }
                }
        }
 
        public class Banking implements Strategy {
 
                TilePath edgeTele = new TilePath(Edge);
                TilePath fallyTele = new TilePath(Fally);
 
                @Override
                public boolean activate() {
                        return Inventory.isFull();
                }
 
                @Override
                public void execute() {
                	if (SceneObjects.getNearest(2213, 11758) !=null){
                		final SceneObject[] Banker = SceneObjects.getNearest(2213, 11758);
                                if (guiId[2] == 1) {
                                        if (Inventory.isFull()
                                                        && Banker[0].getLocation().isOnMinimap()
                                                        && !edgeTele.hasReached()
                                                        && edgeTele != null
                                                        && Players.getLocal() != null) {
                                                if (Banker[0] != null) {
                                                        Trips++;
                                                        INFO = "Banking.";
                                                        edgeTele.traverse();
                                                        Time.sleep(3000);
                                                        if (edgeTele.hasReached()) {
                                                                if (!Banker[0].isOnScreen()) {
                                                                        Camera.turnTo(Banker[0]);
                                                                        Camera.setPitch(true);
                                                                        Time.sleep(500);
                                                                } else {
                                                                        if (Banker[0].getLocation().distanceTo() < 7
                                                                                        && Players.getLocal()
                                                                                                        .getAnimation() == -1) {
                                                                                Banker[0].interact("Use-quickly");
                                                                                Time.sleep(2000);
                                                                                if (Interfaces.getOpenInterfaceId() != -1) {
                                                                                        Mouse.getInstance().click(
                                                                                                        new Point(470, 304), true);
                                                                                        Time.sleep(2500);
                                                                                        Mouse.getInstance().click(
                                                                                                        new Point(486, 41), true);
                                                                                        Time.sleep(2000);
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                                if (guiId[2] == 2) {
                                        if (Inventory.isFull()
                                                        && Banker[1].getLocation().isOnMinimap()
                                                        && !fallyTele.hasReached()
                                        				&& fallyTele != null
                                        				&& Players.getLocal() != null) {
                                                if (Banker[1] != null) {
                                                        Trips++;
                                                        INFO = "Banking.";
                                                        fallyTele.traverse();
                                                        Time.sleep(2000);
                                                        if (fallyTele.hasReached()) {
                                                                if (!Banker[1].isOnScreen()) {
                                                                        Camera.turnTo(Banker[1]);
                                                                        Time.sleep(500);
                                                                } else {
                                                                        if (Banker[1].getLocation().distanceTo() < 5) {
                                                                                Banker[1].interact("Use-quickly");
                                                                                Time.sleep(2000);
                                                                                if (Interfaces.getOpenInterfaceId() != -1) {
                                                                                        Mouse.getInstance().click(
                                                                                                        new Point(470, 304), true);
                                                                                        Time.sleep(2500);
                                                                                        Mouse.getInstance().click(
                                                                                                        new Point(486, 41), true);
                                                                                        Time.sleep(2000);
                                                                                }
                                                                        }
                                                                }
                                                        }
                                                }
                                        }
                                }
                }
        }
        }
 
        public String formatNumber(int start) {
                DecimalFormat form = new DecimalFormat("0.0");
                double i = start;
                if (i >= 1000000) {
                        return form.format((i / 1000000)) + "M/h";
                }
                if (i >= 1000) {
                        return form.format((i / 1000)) + "K/h";
                }
                return "" + start;
        }
 
        public String formatNumb(int start) {
                DecimalFormat form = new DecimalFormat("0.0");
                double i = start;
                if (i >= 1000000) {
                        return form.format((i / 1000000)) + "M";
                }
                if (i >= 1000) {
                        return form.format((i / 1000)) + "K";
                }
                return "" + start;
        }
 
        Image paintImage = getImage("http://i.imgur.com/BRR6LGX.png");
 
        private Image getImage(String url) {
                try {
                        return ImageIO.read(new URL(url));
                } catch (IOException e) {
                        return null;
                }
        }
 
        public String perHr(int gained) {
                return formatNumber((int) ((gained) * 3600000D / (System
                                .currentTimeMillis() - START_TIME)));
        }
 
        public static String TimeRunning(long i) {
                DecimalFormat form = new DecimalFormat("00");
                long millis = System.currentTimeMillis() - i;
                long hours = millis / (1000 * 60 * 60);
                millis -= hours * (1000 * 60 * 60);
                long minutes = millis / (1000 * 60);
                millis -= minutes * (1000 * 60);
                long seconds = millis / 1000;
                return form.format(hours) + "-" + form.format(minutes) + "-"
                                + form.format(seconds);
        }
        
        @Override
        public void messageReceived(MessageEvent me) {
                if (me.getMessage().startsWith("You caught a Dragon")) {
                ImpsCaught++;
                Price += 37875;
                }
                if (me.getMessage().startsWith("You caught a Ninja")) {
                    ImpsCaught++;
                    Price += 34500;
            }
                if (me.getMessage().startsWith("You caught a Magpie")) {
                    ImpsCaught++;
                    Price += 26250;
            }
                if (me.getMessage().startsWith("You caught a Nature")) {
                    ImpsCaught++;
                    Price = 18165;
            }
                if (me.getMessage().startsWith("You caught a Electic")) {
                    ImpsCaught++;
                    Price = 10500;
            }
                if (me.getMessage().startsWith("You caught a Essence")) {
                    ImpsCaught++;
                    Price += 5925;
            }
                if (me.getMessage().startsWith("You caught a Earth")) {
                    ImpsCaught++;
                    Price += 3900;
            }   
                if (me.getMessage().startsWith("You caught a Gourmet")) {
                	ImpsCaught++;
                	Price += 2850;
           }
       			if (me.getMessage().startsWith("You caught a Young")) {
       				ImpsCaught++;
       				Price += 2475;
           }
       			if (me.getMessage().startsWith("You caught a Baby")) {
       				ImpsCaught++;
       				Price += 2250;
        }
               
        }
}