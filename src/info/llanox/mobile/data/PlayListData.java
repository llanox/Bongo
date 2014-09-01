package info.llanox.mobile.data;

import info.llanox.mobile.model.Song;
import info.llanox.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class PlayListData {

	private Context mContext;
	
	public PlayListData(Context context) {
			this.mContext = context;
	}
		
	
	public List<Song> getAllPlaylistSongs(){
		List<Song> songs = new ArrayList<Song>();
		File[] songFiles = FileUtil.getFilesFromSDPath("/Music/");
		
		Song song = null;
		for(File songFile:songFiles){
			song = new Song();
			song.setName(songFile.getName());
			song.setPath(songFile.getPath());
			songs.add(song);
		}
		
		return songs;
		
	}

}
