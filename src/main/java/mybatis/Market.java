package mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

class SerialListener implements SerialPortEventListener {
	private InputStream in;
	private Socket socket;
	private PrintWriter pw;
	String str = "@@Enter 1 ";

	SerialListener(Socket socket, InputStream in) {
		this.socket = socket;
		this.in = in;
		try {
			pw = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		// 이벤트가 발생하면 호출되는 Method
		if (arg0.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				// InputStream에 Data가 존재하는지 확인
				// return값은 size를 의미하므로 k는 전달되는 Data의 크기를 의미한다.
				int k = in.available();
				// 데이터의 size를 알기 때문에 그만큼만 배열을 선언
				byte[] data = new byte[k];
				in.read(data, 0, k);
				String tmp = new String(data);
				if(tmp.contains("\n")) {
					str += tmp;
					System.out.println(str.trim());
					pw.println(str.trim());
					pw.flush();
					str = "@@Enter 1 ";
				} else {
					str += tmp;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

public class Market {
	static InputStream in;
	static PrintWriter pw;

	public static void main(String[] args) {
		Socket socket;
		CommPortIdentifier portIdentifier = null;
		try {
			socket = new Socket("localhost", 6020);
			portIdentifier = CommPortIdentifier.getPortIdentifier("COM9");

			if (portIdentifier.isCurrentlyOwned()) {
				System.out.println("Port is used");
			} else {
				CommPort commPort = portIdentifier.open("PORT_OPEN", 2000);
				if (commPort instanceof SerialPort) {
					SerialPort serialPort = (SerialPort) commPort;
					serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);

					InputStream in = serialPort.getInputStream();

					serialPort.addEventListener(new SerialListener(socket, in));
					// Data가 들어오면 신호를 줘서 Event를 진행
					serialPort.notifyOnDataAvailable(true);
				} else {
					System.out.println("Serial Port is only usable");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
