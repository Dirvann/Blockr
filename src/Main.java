import canvaswindow.CanvasWindow;



class Main {
	public static void main(String[] args) {
	     java.awt.EventQueue.invokeLater(() -> {
	          new CanvasWindow("My Canvas Window").show();
	     });
	 }
}

