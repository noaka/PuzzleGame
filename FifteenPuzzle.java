
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;


/// Noa Kafry 15 Puzzle Project///////
/// 3.6.19 ////////////
    class FifteenPuzzle extends JPanel {

        private final int size = 4;
        private final int numSquare = size * size - 1;

        private final Random rand = new Random();
        private final int[] board = new int[numSquare + 1];
        private int blankSquare;
        private  int tileSize;
        private int gridSize;
        private  int margin;
        private boolean isGameOver;

        public FifteenPuzzle() {

            guiConf();
            isGameOver = true;

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (isGameOver) {
                        newGame();

                    } else {

                        int ex = e.getX() - margin;
                        int ey = e.getY() - margin;

                        if (ex < 0 || ex > gridSize || ey < 0 || ey > gridSize) {
                            return;
                        }

                        int c1 = ex / tileSize;
                        int r1 = ey / tileSize;
                        int c2 = blankSquare % size;
                        int r2 = blankSquare / size;

                        int clickPos = r1 * size + c1;

                        int dir = 0;
                        if (c1 == c2 && Math.abs(r1 - r2) > 0) {
                            dir = (r1 - r2) > 0 ? 4 : -4;

                        } else if (r1 == r2 && Math.abs(c1 - c2) > 0) {
                            dir = (c1 - c2) > 0 ? 1 : -1;
                        }

                        if (dir != 0) {
                            do {
                                int newBlankPos = blankSquare + dir;
                                board[blankSquare] = board[newBlankPos];
                                blankSquare = newBlankPos;
                            } while (blankSquare != clickPos);
                            board[blankSquare] = 0;
                        }

                        isGameOver = EOG();
                    }
                    repaint();
                }
            });

            newGame();
        }

    private void guiConf() {
        final int dim = 640;
        margin = 80;
        tileSize = (dim - 2 * margin) / size;
        gridSize = tileSize * size;

        setPreferredSize(new Dimension(dim, dim + margin));
        setBackground(Color.WHITE);
        setForeground(new Color(0x6495ED)); // cornflowerblue
        setFont(new Font("david", Font.BOLD, 60));
    }

    private void newGame() {
            do {
                reset();
            } while (!checkEOG());
            isGameOver = false;
        }

        private void reset() {

            for (int i = 0; i < board.length; i++) {
                board[i] = (i + 1) % board.length;
            }
            blankSquare = board.length - 1;

            //add random numbers
            int n = numSquare;
            while (n > 1) {
                int r = rand.nextInt(n--);
                int tmp = board[r];
                board[r] = board[n];
                board[n] = tmp;
            }

        }

//check how many moves will have
        private boolean checkEOG() {
            int option = 0;
            for (int i = 0; i < numSquare; i++) {
                for (int j = 0; j < i; j++) {
                    if (board[j] > board[i]) {
                        option++;
                    }
                }
            }
            return option % 2 == 0;
        }

        //check if every num is in his position
        private boolean EOG() {
            if (board[board.length - 1] != 0) {
                return false;
            }
            for (int i = numSquare - 1; i >= 0; i--) {
                if (board[i] != i + 1) {
                    return false;
                }
            }
            return true;
        }

        //extend from Jpanel axtend jcomponent
    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        Graphics2D g = (Graphics2D) graph;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawGame(g);
        drawStartMessage(g);
    }

        private void drawGame(Graphics2D graph2d) {
            for (int i = 0; i < board.length; i++) {
                int r = i / size;
                int c = i % size;
                int x = margin + c * tileSize;
                int y = margin + r * tileSize;

                if (board[i] == 0) {
                    if (isGameOver) {
                        graph2d.setColor(Color.yellow);
                        drawCenteredString(graph2d, "Game Over", x, y);
                    }
                    continue;
                }

                graph2d.setColor(getForeground());
                graph2d.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
                graph2d.setColor(Color.blue.darker());
                graph2d.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
                graph2d.setColor(Color.WHITE);

                drawCenteredString(graph2d, String.valueOf(board[i]), x, y);
            }
        }

    private void drawCenteredString(Graphics2D g, String s, int x, int y) {
        FontMetrics fm = g.getFontMetrics();
        int asc = fm.getAscent();
        int des = fm.getDescent();

        x = x + (tileSize - fm.stringWidth(s)) / 2;
        y = y + (asc + (tileSize - (asc + des)) / 2);

        g.drawString(s, x, y);
    }

    private void drawStartMessage(Graphics2D g) {
        if (isGameOver) {
            g.setFont(getFont().deriveFont(Font.BOLD, 18));
            g.setColor(getForeground());
            String s = "click on any piece to start a new game";
            int x = (getWidth() - g.getFontMetrics().stringWidth(s)) / 2;
            int y = getHeight() - margin;
            g.drawString(s, x, y);
        }
        }







}


