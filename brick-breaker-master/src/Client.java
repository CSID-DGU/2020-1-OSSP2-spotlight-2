import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	Socket socket;

	void startClient() {
		// connect()와 read() 메소드는 블로킹 되기 때문에 별도의 스레드를 생성해서 처리합니다.
		Thread thread = new Thread() {
			@Override
			public void run() {
				socket = new Socket();
				try {
					System.out.println("[서버에 연결 요청 중]");
					socket.connect(new InetSocketAddress("15.164.215.115", 8888));
					System.out.println("[서버와 연결 완료]");
				} catch (IOException e) {
					System.out.println("[서버와 통신 안됨]");
					if(!socket.isClosed()) {
						stopClient();
					}
					return;
				}
				//receive();
			}
		};
		thread.start();
	}

	void stopClient() {
		if(socket!=null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	

}
