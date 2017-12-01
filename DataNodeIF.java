import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.IOException;
import java.io.FileNotFoundException;

public interface DataNodeIF extends Remote {

	public String ReadArchive(String nameA) throws RemoteException, IOException, FileNotFoundException;

	public boolean WriteArchive(String nameA, String text, boolean append) throws RemoteException, IOException;

	public boolean CreateArchive(String nameA) throws RemoteException, IOException;

	public boolean DeleteArchive(String nameA) throws RemoteException, IOException;
}