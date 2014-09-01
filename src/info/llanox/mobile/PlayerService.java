package info.llanox.mobile;



import info.llanox.mobile.data.DBRepository;

import java.io.IOException;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Esta clase es un servicio o proceso en background que reproduce un 
 * archivo multimedia. Los servicios no estan asociados a una interface
 * gráfica como si lo estan las actividades, pero pueden estar atados a
 * una actividad. Esto quiere decir que la existencia del Servicio puede estar
 * ligada a una Actividad.
 * 
 * @author llanox 
 * **/

public class PlayerService extends Service {

	private MediaPlayer mediaPlayer; 
	
	public static final String COMMAND_PLAY="play";
	public static final String COMMAND_PAUSE="pause";
	public static final String COMMAND_FORWARD="forward";
	public static final String COMMAND_REWARD="reward";
	public static final String COMMAND_KEY="command";
	public DBRepository adapter = null;

	public static final String PATH = "path";
	public String lastPath = null; 
	
	
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;			
	}

	/**
	 * Este es el metodo que es llamado por el sistema
	 * para crear el servicio
	 * */
	@Override
	public void onCreate() {
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setLooping(false); // Set looping
		
		adapter = new DBRepository(getApplicationContext());
		adapter.open();
		
	}

	@Override
	public void onDestroy (  ) {
		mediaPlayer.stop();
		adapter.close();
		clean();
		
	}
	
	

	private void clean() {
		mediaPlayer.release();
		mediaPlayer = null;
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		
		Bundle bundle = intent.getExtras();
		String command = bundle.getCharSequence(COMMAND_KEY).toString();
		String path = bundle.getCharSequence(PATH).toString();
		
		try {
	    	
        SharedPreferences preferences =  this.getApplicationContext().getSharedPreferences("playlist", 0);        
       
        //TODO almcenar la posicion actual de la cancion que se esta reproduciendo 
        // para empezar reproducirla desde la posicion almacenada
        
    
		
		Toast.makeText(this, "command:"+bundle.getCharSequence(COMMAND_KEY), Toast.LENGTH_SHORT).show();
		
		if(COMMAND_PAUSE.equalsIgnoreCase(command)){
			if(mediaPlayer.isPlaying()){
				mediaPlayer.pause();
			}
		}
		
		if(COMMAND_PLAY.equalsIgnoreCase(command)){
			
			mediaPlayer.reset();
			mediaPlayer.setDataSource(path);		
			mediaPlayer.prepare();			
			mediaPlayer.start();
			adapter.insertReproduccion(path, new Date());
		
		}
			
		
		} catch (IllegalArgumentException e) {
		    Log.e("PlayerService", "",e);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			Log.e("PlayerService", "",e);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			Log.e("PlayerService", "",e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("PlayerService", "",e);
		}
	}
}
