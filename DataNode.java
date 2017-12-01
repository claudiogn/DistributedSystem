import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.Naming;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.*;
import java.io.FileNotFoundException;

public class DataNode extends UnicastRemoteObject implements DataNodeIF {

	public DataNode() throws RemoteException{
		super();
	}
	

	public String ReadArchive(String nameA) throws IOException, FileNotFoundException {
		File file = new File("C:\\Users\\Claudio\\Desktop\\"+nameA+".txt");
		
		if(file.exists()){
			String data = "";
	       	FileReader fileReader = new FileReader(file);
	   		BufferedReader reader = new BufferedReader(fileReader);
	   		String linha = null;
	   		
	   		while((linha = reader.readLine()) != null){
				data = data + linha + "\n";
			}
			fileReader.close();
			reader.close();
			return data;
        }
       	return null;
		
	}

	public boolean WriteArchive(String nameA, String text, boolean append) throws IOException{
		File file = new File("C:\\Users\\Claudio\\Desktop\\"+nameA+".txt");

        if(file.exists()){

	       	FileWriter arq = new FileWriter("C:\\Users\\Claudio\\Desktop\\"+nameA+".txt",append);
	   		PrintWriter gravarArq = new PrintWriter(arq);
	   		gravarArq.println(text);
	   		gravarArq.close();
	   		arq.close();
	   		return true;
        }	
		return false;
	}

	public boolean CreateArchive(String nameA) throws IOException{
		File file = new File("C:\\Users\\Claudio\\Desktop\\"+nameA+".txt");

        if(!file.exists()){

	       	FileWriter arq = new FileWriter("C:\\Users\\Claudio\\Desktop\\"+nameA+".txt");
	       	arq.close();
	   		return true;

        }	
		return false;
	}

	public boolean DeleteArchive(String nameA) throws IOException{
		File file = new File("C:\\Users\\Claudio\\Desktop\\"+nameA+".txt");

        if(file.exists()){
        	System.out.println("Existe.");
	       	if(file.delete()){
	       		System.out.println("Deletou.");
	       		return true;	
	       	}   		

        }
		return false;
	}
	
	
	public static void main(String args[])
	{
		try {
			
            DataNode servidor = new DataNode();
            Naming.bind("Nodesystem", servidor);
            System.err.println("Datanode pronto para uso.");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
	}




}