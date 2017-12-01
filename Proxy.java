import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.Naming;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Proxy extends UnicastRemoteObject implements ProxyIF
{
	boolean find = false;
	String data = null;

	public Proxy() throws RemoteException{
		super();
	}
	
	public String Read(String nameA){
		try{			
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Nodesystem");
		data = obj.ReadArchive(nameA);
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}

		//Integer servidor = hashCode(nameA);
		//String datanode = Integer.toString(servidor);
		//DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/"+datanode);
		return data;
	}

	public boolean Write(String nameA, String text, boolean append){

	try{			
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Nodesystem");
		find = obj.WriteArchive(nameA, text, append);
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		return find;
	}

	public boolean Create(String nameA){
		try{			
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Nodesystem");
		find = obj.CreateArchive(nameA);
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		return find;
	}

	public boolean Delete(String nameA){
		try{			
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Nodesystem");
		find = obj.DeleteArchive(nameA);
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		return find;
	}

  	public int hashCode(String str){
    	return str.hashCode()%3;
  	}


	public static void main(String args[])
	{
		try {
			
            Proxy servidor = new Proxy();
            Naming.bind("Filesystem", servidor);
            System.err.println("Proxy pronto para uso.");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
	}

}