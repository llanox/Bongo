package info.llanox.mobile.adapters;

import info.llanox.mobile.R;
import info.llanox.mobile.R.id;
import info.llanox.mobile.model.Song;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SongAdapter extends ArrayAdapter<Song> {

	int layoutId;
	OnClickListener listener;
	
	public SongAdapter(Context context, int resource,int textViewResourceId, List<Song> objects, OnClickListener listener) {
		super(context, resource, textViewResourceId, objects);
		this.layoutId = resource;
		this.listener = listener;
	}

	public SongAdapter(Context context, int textViewResourceId,	List<Song> objects) {
		super(context, textViewResourceId, objects);
	
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		
		if(view==null){
			
			  //Se obtiene el inflador
            LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);            
            view = vi.inflate(layoutId, null);
			
		}
		
		Song song = this.getItem(position);
		
		TextView tv = (TextView) view.findViewById(R.id.tv_nombre_cancion);
		tv.setText(song.getNombre());
		
		tv = (TextView) view.findViewById(R.id.tv_album);
		tv.setText(song.getAlbum());
		
		tv = (TextView) view.findViewById(R.id.tv_artista);
		tv.setText(song.getArtist());
		
		tv = (TextView) view.findViewById(R.id.tv_duracion);
		tv.setText(song.getDuration()+" ms");
		
		ImageButton bt = (ImageButton) view.findViewById(R.id.ib_media);
		bt.setOnClickListener(listener);
		bt.setTag(song.getPath());

		return view;
	}
	
	
	
	
	

}
