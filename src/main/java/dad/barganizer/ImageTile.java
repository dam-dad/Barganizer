package dad.barganizer;

import java.io.ByteArrayInputStream;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.Tile.ImageMask;
import eu.hansolo.tilesfx.Tile.SkinType;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;

public class ImageTile {

	private static final int TILE_WIDTH = 125;
	private static final int TILE_HEIGHT = 125;
	
	private Tile tile;
	
	public ImageTile(byte[] imagen, String titulo) {
		
		tile = TileBuilder.create()
				.skinType(SkinType.IMAGE)
				.prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title(titulo)
                .image((imagen != null) ? new Image(new ByteArrayInputStream(imagen)) : null)
                .imageMask(ImageMask.ROUND)
                .text("")
                .textAlignment(TextAlignment.CENTER)
				.build();
		
	}
	
	public ImageTile(byte[] imagen, String titulo, String texto) {
		tile = TileBuilder.create()
				.skinType(SkinType.IMAGE)
				.prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title(titulo)
                .image((imagen != null) ? new Image(new ByteArrayInputStream(imagen)) : null)
                .imageMask(ImageMask.ROUND)
                .text(texto)
                .textAlignment(TextAlignment.CENTER)
				.build();
	}
	
	public Tile getTile() {
		return tile;
	}
}
