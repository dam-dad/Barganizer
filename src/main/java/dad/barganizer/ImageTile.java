package dad.barganizer;

import java.io.ByteArrayInputStream;

import dad.barganizer.db.beans.Bebida;
import dad.barganizer.db.beans.Empleado;
import dad.barganizer.db.beans.Mesa;
import dad.barganizer.db.beans.Plato;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.Tile.ImageMask;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.Tile.TextSize;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.text.TextAlignment;

public class ImageTile extends Tile {

	private static final int TILE_WIDTH = 125;
	private static final int TILE_HEIGHT = 125;

	private ObjectProperty<Object> referencia = new SimpleObjectProperty<Object>();

	private Tile tile;

	public ImageTile(Bebida b) {
		setSkinType(SkinType.IMAGE);
		setPrefSize(TILE_WIDTH, TILE_HEIGHT);
		setTitle("asdasdasd");
		setImage((b.getFoto() != null) ? new Image(new ByteArrayInputStream(b.getFoto())) : null);
		setImageMask(ImageMask.ROUND);
		setText(b.getNombre());
		setTextAlignment(TextAlignment.CENTER);
		setTextSize(TextSize.BIGGER);
		setReferencia(b);

//		tile = TileBuilder.create().skinType(SkinType.IMAGE).prefSize(TILE_WIDTH, TILE_HEIGHT).title(b.getNombre())
//				.titleAlignment(TextAlignment.CENTER).textSize(TextSize.BIGGER)
//				.image((b.getFoto() != null) ? new Image(new ByteArrayInputStream(b.getFoto())) : null).imageMask(ImageMask.ROUND)
//				.text("").textAlignment(TextAlignment.CENTER).build();
	}

	public ImageTile(Mesa m) {
		setSkinType(SkinType.IMAGE);
		setPrefSize(TILE_WIDTH, TILE_HEIGHT);
		setImage(new Image(getClass().getResourceAsStream("/images/mesa.png")));
		setImageMask(ImageMask.ROUND);
		setText("" + m.getNumero());
		setTextAlignment(TextAlignment.CENTER);
		setTextSize(TextSize.BIGGER);
		setReferencia(m);

//		tile = TileBuilder.create().skinType(SkinType.IMAGE).prefSize(TILE_WIDTH, TILE_HEIGHT).title(""+m.getNumero())
//				.titleAlignment(TextAlignment.CENTER).textSize(TextSize.BIGGER)
//				.image(new Image(getClass().getResourceAsStream("/images/mesa.png"))).imageMask(ImageMask.ROUND)
//				.text("Personas: " + m.getCantPersonas()).textAlignment(TextAlignment.CENTER).build();
	}

	public ImageTile(Plato p) {
		setSkinType(SkinType.IMAGE);
		setTitleAlignment(TextAlignment.CENTER);
		setTitle("Título");
		setPrefSize(TILE_WIDTH, TILE_HEIGHT);
		setImage((p.getFoto() != null) ? new Image(new ByteArrayInputStream(p.getFoto())) : null);
		setImageMask(ImageMask.ROUND);
		setText(p.getNombre());
		setTextAlignment(TextAlignment.CENTER);
		setTextSize(TextSize.BIGGER);
		setReferencia(p);

//		tile = TileBuilder.create().skinType(SkinType.IMAGE).prefSize(TILE_WIDTH, TILE_HEIGHT).title(p.getNombre())
//				.titleAlignment(TextAlignment.CENTER).textSize(TextSize.BIGGER)
//				.image((p.getFoto() != null) ? new Image(new ByteArrayInputStream(p.getFoto())) : null).imageMask(ImageMask.ROUND)
//				.text("").textAlignment(TextAlignment.CENTER).build();
	}

	public ImageTile(Empleado e) {
		setSkinType(SkinType.IMAGE);
		setTitleAlignment(TextAlignment.CENTER);
		setTitle("Título");
		setPrefSize(TILE_WIDTH, TILE_HEIGHT);
		setImage((e.getFoto() != null) ? new Image(new ByteArrayInputStream(e.getFoto())) : null);
		setImageMask(ImageMask.ROUND);
		setText(e.getNombre() + " " + e.getApellidos());
		setTextAlignment(TextAlignment.CENTER);
		setTextSize(TextSize.BIGGER);
		setReferencia(e);
	}

	public ImageTile(byte[] imagen, String titulo) {

		tile = TileBuilder.create().skinType(SkinType.IMAGE).prefSize(TILE_WIDTH, TILE_HEIGHT).title(titulo)
				.titleAlignment(TextAlignment.CENTER).textSize(TextSize.BIGGER)
				.image((imagen != null) ? new Image(new ByteArrayInputStream(imagen)) : null).imageMask(ImageMask.ROUND)
				.text("").textAlignment(TextAlignment.CENTER).build();

	}

	public ImageTile(byte[] imagen, String titulo, String texto) {
		tile = TileBuilder.create().skinType(SkinType.IMAGE).prefSize(TILE_WIDTH, TILE_HEIGHT)
				.titleAlignment(TextAlignment.CENTER).textSize(TextSize.BIGGER).title(titulo)
				.image((imagen != null) ? new Image(new ByteArrayInputStream(imagen)) : null).imageMask(ImageMask.ROUND)
				.text(texto).textAlignment(TextAlignment.CENTER).build();
	}

	public Tile getTile() {
		return tile;
	}

	public final ObjectProperty<Object> referenciaProperty() {
		return this.referencia;
	}

	public final Object getReferencia() {
		return this.referenciaProperty().get();
	}

	public final void setReferencia(final Object referencia) {
		this.referenciaProperty().set(referencia);
	}

}
