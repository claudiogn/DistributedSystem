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
	
	String data = null;
	String datanode = null;

	public Proxy() throws RemoteException{
		super();
	}
	
	public String Read(String nameA){
	try{
		System.out.println("- Solicitação de leitura do arquivo "+nameA+".txt");			
		NameNodeIF name = (NameNodeIF) Naming.lookup("//"+""+"/Namenode");
		datanode = name.GetDataNode(nameA);
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+datanode);
		data = obj.ReadArchive(nameA, datanode);
		if(data != null){
			System.out.println("- Arquivo "+nameA+".txt"+" encontrado no Datanode "+datanode);
		}else {
			System.out.println("- Arquivo "+nameA+".txt"+" não encontrado.");
		}
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		return data;
	}

	public boolean Write(String nameA, String text, boolean append){
	boolean find = false;
	try{
		System.out.println("- Solicitação de escrita no arquivo "+nameA+".txt");
		NameNodeIF name = (NameNodeIF) Naming.lookup("//"+""+"/Namenode");
		datanode = name.GetDataNode(nameA);
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+datanode);		
		find = obj.WriteArchive(nameA, text, append, datanode);
		if(find){
			System.out.println("- Arquivo "+nameA+".txt"+" atualizado no Datanode "+datanode);
		}else{
			System.out.println("- Arquivo "+nameA+".txt"+" não encontrado.");
		}
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		return find;
	}

	public boolean Create(String nameA){
	boolean find = false;
	try{
		System.out.println("- Solicitação de criacao do arquivo "+nameA+".txt");
		NameNodeIF name = (NameNodeIF) Naming.lookup("//"+""+"/Namenode");
		datanode = name.GetDataNode(nameA);
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+datanode);			
		find = obj.CreateArchive(nameA,datanode);
		if(find){
			System.out.println("- Arquivo "+nameA+".txt"+" criado no Datanode "+datanode);
		}else{
			System.out.println("- O arquivo "+nameA+".txt"+" já existe no Datanode "+datanode);
		}
		}catch(Exception e){
			System.out.println("FALHA NA CONEXÃO COM O SERVIDOR.");
			System.out.println(e.getMessage());
		}
		return find;
	}

	public boolean Delete(String nameA){
	boolean find = false;
	try{
		System.out.println("- Solicitação de delete do arquivo "+nameA+".txt");
		NameNodeIF name = (NameNodeIF) Naming.lookup("//"+""+"/Namenode");
		datanode = name.GetDataNode(nameA);
		DataNodeIF obj = (DataNodeIF) Naming.lookup("//"+""+"/Datanode"+datanode);			
		find = obj.DeleteArchive(nameA,datanode);
		if(find){
			System.out.println("- Arquivo "+nameA+".txt"+" deletado do Datanode "+datanode);
		}else{
			System.out.println("- Arquivo "+nameA+".txt"+" não encontrado.");
		}
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
            Naming.bind("Proxy", servidor);
            System.err.println("Proxy pronto para uso.");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
	}

}