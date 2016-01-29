package magic.ui.widget.deck;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import magic.model.MagicDeck;
import magic.model.DuelPlayerConfig;
import magic.translate.UiString;
import magic.ui.widget.TitleBar;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class DeckDescriptionViewer extends JPanel {

    // translatable strings
    private static final String _S1 = "Deck Description";

    private final JTextArea textArea;
    private final JScrollPane scrollPane;

    public DeckDescriptionViewer() {

        setOpaque(false);

        final TitleBar titleBar = new TitleBar(UiString.get(_S1));

        textArea = new JTextArea();
        textArea.setOpaque(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.setBorder(null);

        setMinimumSize(titleBar.getMinimumSize());

        final MigLayout mig = new MigLayout();
        setLayout(mig);        
        mig.setLayoutConstraints("flowy, insets 0, gap 0");
        mig.setColumnConstraints("[fill, grow]");
        mig.setRowConstraints("[][fill, grow]");
        add(titleBar);
        add(scrollPane);

    }

    public void setDeckChooserLayout() {
        setBorder(null);
        setOpaque(false);
        //
        textArea.setForeground(Color.BLACK);
        textArea.setBackground(new Color(0, 0, 0, 1));
        textArea.setFont(new Font("dialog", Font.ITALIC, 12));
        textArea.setOpaque(false);
        textArea.setFocusable(false);
        //
        scrollPane.setViewportView(textArea);
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBackground(Color.RED);
        scrollPane.setBorder(null);
        //
        removeAll();
        setLayout(new MigLayout("insets 3"));
        add(scrollPane, "w 100%, h 100%");
        revalidate();
    }

    public void setPlayer(final DuelPlayerConfig playerDef) {
        setDeckDescription(playerDef.getDeck().getDescription());
    }

    public void setDeckDescription(final String text) {
        textArea.setText(text == null || text.isEmpty() ? "" : text.replaceAll("\\\\n", "\n").trim());
        textArea.setCaretPosition(0);
    }

    public void setDeckDescription(final MagicDeck deck) {
        if (deck != null) {
            setDeckDescription(deck.getDescription());
            textArea.setForeground(deck.isValid() ? Color.BLACK : Color.RED.darker());
        } else {
            setDeckDescription("");
        }
    }

}