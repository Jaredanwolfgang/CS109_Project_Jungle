package view;

import listener.HoverListener;
import view.ChessComponent.ChessComponent;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color normalBackground;
    private Color hoverBackground;
    private Color labelledBackground;
    private boolean hovered;
    private boolean labelled;
    private int cornerRadius;
    private HoverListener hoverListener;

    public CellComponent(Color normalBackground, Color hoverBackground, Color labelledBackground, Point location, int size) {
        setLayout(new GridLayout(1, 1));
        setLocation(location);
        setSize(size, size);
        this.normalBackground = normalBackground;
        this.hoverBackground = hoverBackground;
        this.labelledBackground = labelledBackground;
        this.cornerRadius = size / 10;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (hoverListener != null) {
                    hoverListener.onHovered(CellComponent.this);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (hoverListener != null) {
                    hoverListener.onExited(CellComponent.this);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Trigger the click event in ChessboardComponent
                getParent().dispatchEvent(new MouseEvent(getParent(), e.getID(), e.getWhen(), e.getModifiersEx(), getLocation().x + e.getX(), getLocation().y + e.getY(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
            }
        });
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public void setLabelled(boolean labelled) {
        this.labelled = labelled;
    }

    public void setHoverListener(HoverListener hoverListener) {
        this.hoverListener = hoverListener;
    }

    public void setBackground(Color normalBackground,Color hoverBackground,Color labelledBackground) {
        this.normalBackground = normalBackground;
        this.hoverBackground = hoverBackground;
        this.labelledBackground = labelledBackground;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;

        if (labelled) {
            g.setColor(labelledBackground);
        } else if (!labelled && hovered) {
            g.setColor(hoverBackground);
        } else {
            g.setColor(normalBackground);
        }
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(1, 1, this.getWidth() - 1, this.getHeight() - 1, cornerRadius, cornerRadius);
        g2d.fill(roundedRectangle);
        RoundBorder roundBorder = new RoundBorder(cornerRadius, Color.LIGHT_GRAY);
        setBorder(roundBorder);
    }

    public static class RoundBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(color);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius + 1;
            insets.top = radius + 1;
            insets.bottom = radius + 2;
            return insets;
        }
    }
}
