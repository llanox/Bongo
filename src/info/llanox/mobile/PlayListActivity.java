package info.llanox.mobile;

import info.llanox.mobile.adapters.SongAdapter;
import info.llanox.mobile.data.PlayListData;
import info.llanox.mobile.model.Song;

import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class PlayListActivity extends ListActivity implements OnClickListener {

	private static final String PATH_CANCION_ACTUAL = "PATH_CANCION_ACTUAL";

	private static final int SONG_STATUS = 234;
	private static final int STATUS_SONG = 0;

	private boolean playing = false;
	private ImageButton anteriorCancion = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadSongs();

	}

	@Override
	protected void onResume() {
		super.onResume();
		loadSongs();
	}

	private void loadSongs() {
		PlayListData data = new PlayListData(this);
		List<Song> canciones = data.getAllPlaylistSongs();
		SongAdapter adapter = new SongAdapter(this,
				R.layout.list_cancion_item, R.id.tv_album, canciones, this);
		setListAdapter(adapter);
	}

	@Override
	public void onClick(View v) {

		String command = null;

		ImageButton ibPlayOrButton = (ImageButton) v;

		String path = ibPlayOrButton.getTag().toString();
		SharedPreferences pref = this.getPreferences(MODE_PRIVATE);
		String pathCancionActual = pref.getString(PATH_CANCION_ACTUAL, null);

		if (pathCancionActual == null) {
			pathCancionActual = path;
		}

		if (!playing && path.equalsIgnoreCase(pathCancionActual)) {
			// establecemos a play como el comando a enviar al servicio
			command = PlayerService.COMMAND_PLAY;
			playing = true;
			// Cambiamos la imagen del boton
			ibPlayOrButton.setImageResource(android.R.drawable.ic_media_pause);
			SharedPreferences.Editor edit = pref.edit();
			edit.putString(PATH_CANCION_ACTUAL, path);
			edit.commit();
			anteriorCancion = ibPlayOrButton;
			commandToPlayer(command, path);
			return;
		}

		if (!playing && !path.equalsIgnoreCase(pathCancionActual)) {
			// establecemos a play como el comando a enviar al servicio
			command = PlayerService.COMMAND_PLAY;
			playing = true;
			ibPlayOrButton.setImageResource(android.R.drawable.ic_media_pause);
			// Cambiamos la imagen del boton
			ibPlayOrButton.setImageResource(android.R.drawable.ic_media_pause);
			SharedPreferences.Editor edit = pref.edit();
			edit.putString(PATH_CANCION_ACTUAL, path);
			edit.commit();

			if (anteriorCancion != null)
				anteriorCancion.setImageResource(android.R.drawable.ic_media_play);

			anteriorCancion = ibPlayOrButton;
			commandToPlayer(command, path);
			return;

		}

		if (playing && path.equalsIgnoreCase(pathCancionActual)) {
			// establecemos a pause como el comando a enviar al servicio
			command = PlayerService.COMMAND_PAUSE;
			playing = false;
			// Cambiamos la imagen del boton
			ibPlayOrButton.setImageResource(android.R.drawable.ic_media_play);
			anteriorCancion = ibPlayOrButton;
			commandToPlayer(command, path);
			return;

		}

		if (playing && !path.equalsIgnoreCase(pathCancionActual)) {
			// establecemos a pause como el comando a enviar al servicio
			command = PlayerService.COMMAND_PLAY;
			playing = false;
			// Cambiamos la imagen del boton
			ibPlayOrButton.setImageResource(android.R.drawable.ic_media_pause);

			if (anteriorCancion != null)
				anteriorCancion.setImageResource(android.R.drawable.ic_media_play);

			anteriorCancion = ibPlayOrButton;
			commandToPlayer(command, path);
			return;

		}

	}

	private void commandToPlayer(String command, String... parameters) {

		Intent intent = new Intent(this, PlayerService.class);
		Bundle bundle = new Bundle();

		bundle.putCharSequence(PlayerService.COMMAND_KEY, command);
		bundle.putCharSequence(PlayerService.PATH, parameters[0]);
		intent.putExtras(bundle);

		startService(intent);
	}
}
