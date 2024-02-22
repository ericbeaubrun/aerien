package ihm.paint;

import data.Entity;
import engine.SelectionListener;
import ihm.buttons.DisplayToggle;

import java.awt.*;

import static config.Config.BLOCK_SIZE;
import static config.IHMConfig.HIGHLIGHT_ENTITY_COLOR;

public class EntityHighlighter {

    private final DisplayToggle displayToggle;

    public EntityHighlighter(DisplayToggle displayToggle) {
        this.displayToggle = displayToggle;
    }

    public void highlightAirplaneAirport(Graphics g, SelectionListener selectionListener) {
        Entity airplane = selectionListener.getSelectedAirplane();
        Entity airport = selectionListener.getSelectedAirport();

        if (airplane != null) {
            highlightEntity(g, airplane);
        } else if (airport != null) {
            highlightEntity(g, airport);
        }
    }

    private void highlightEntity(Graphics g, Entity entity) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(HIGHLIGHT_ENTITY_COLOR);
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.3f));
        g2d.fillOval(entity.getX(), entity.getY(), BLOCK_SIZE, BLOCK_SIZE);
        g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
    }
}