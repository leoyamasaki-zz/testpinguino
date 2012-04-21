package roboticachiapas.com;

import android.app.Activity;
import java.io.IOException;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TestPinguinoActivity extends Activity {
	
	private Button Stop;
	private Button Forward;
	private Button Backward;
	private Button Left;
	private Button Right;	
	private Button Connect;
	private Button DisConnect;
	private TextView Tittle;
	
	boolean tcp_connected=false;
	
	Server tcpserver = null;
	
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
            
        // create layout event
        
        this.Stop = (Button)this.findViewById(R.id.Stop);
        this.Forward = (Button)this.findViewById(R.id.Forward);
        this.Backward = (Button)this.findViewById(R.id.Backward);
        this.Left = (Button)this.findViewById(R.id.Left);
        this.Right = (Button)this.findViewById(R.id.Right);       
        this.Connect = (Button)this.findViewById(R.id.Connect);
        this.DisConnect = (Button)this.findViewById(R.id.DisConnect);
        this.Tittle = (TextView)this.findViewById(R.id.Tittle);
        
        Stop.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) { 
        		if (tcp_connected)
        			{
		        		try
		        		{
		        			tcpserver.send("ST");
		        			Tittle.setText("STOP!!");		        			
		        		} catch (IOException e)
		        		{
		        			Log.e("pinguino", "problem sending TCP message", e);
		        		}	
        			}
        		else Tittle.setText("Start Server then connect Pinguino !!");
        	}
        });

        Forward.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		if (tcp_connected)
    			{
	        		try
	        		{
	        			tcpserver.send("FW");
	        			Tittle.setText("Going forward!!");
	        		} catch (IOException e)
	        		{
	        			Log.e("pinguino", "problem sending TCP message", e);
	        		}	
    			}
        		else Tittle.setText("Start Server then connect Pinguino !!");
        	}
        });

        Backward.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) { 
        		if (tcp_connected)
    			{
	        		try
	        		{
	        			tcpserver.send("BW");
	        			Tittle.setText("Going Back!!");
	        		} catch (IOException e)
	        		{
	        			Log.e("pinguino", "problem sending TCP message", e);
	        		}	        		
    			}
        		else Tittle.setText("Start Server then connect Pinguino !!");        		
        	}
        });

        Left.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		if (tcp_connected)
        		{
	        		try
	        		{
	        			tcpserver.send("LF");
	        			Tittle.setText("Turn left!!");
	        		} catch (IOException e)
	        		{
	        			Log.e("pinguino", "problem sending TCP message", e);
	        		}	        		        		
        		}
        		else Tittle.setText("Start Server then connect Pinguino !!");
        	}
        });

        Right.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) { 
        		if (tcp_connected)
        		{
	        		try
	        		{
	        			tcpserver.send("RT");
	        			Tittle.setText("Turn Right!!");
	        		} catch (IOException e)
	        		{
	        			Log.e("pinguino", "problem sending TCP message", e);
	        		}	        		        		
        		}
        		else Tittle.setText("Start Server then connect Pinguino !!");
        	}
        });  
        
        Connect.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		if (tcp_connected==false)
        		{
	        		try
	        		{
	        			tcpserver=new Server(2000);
	        			tcpserver.start();        			
	        			Tittle.setText("Server started !!");
	        			tcp_connected=true;
	        			
	        			tcpserver.addListener(new AbstractServerListener() {
	        				
	        				@Override
	        				public void onReceive(Client client, byte[] data)
	        				{
	        					String chaine="";
	        					
	        					for(int i=0; i<data.length; i++)
	        					       chaine = chaine + (char)data[i];

	        	        		try
	        	        		{
	        	        			tcpserver.send(chaine);
	        	        		} catch (IOException e)
	        	        		{
	        	        			Log.e("pinguino", "problem sending TCP message", e);
	        	        			return;
	        	        		}
	        					//Tittle.setText(chaine);

	        				};
	        				
	        			});	
	        			
	        		} catch (IOException e)
	        		{
	        		Tittle.setText("Pinguino-Daneel is not connected !!");
	        		Log.e("pinguino","unable to start server",e);
	        		tcp_connected=false;
	        		}
        		}
	        	else Tittle.setText("Already connected !!");
	        			
        		
        	}
        });  
        
        DisConnect.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		tcpserver.stop();        			
				Tittle.setText("Server closed !!");
				tcp_connected=false;        		
        	}
        });          
    }
}