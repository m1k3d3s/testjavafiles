package mpd.input;

public class Order {

	private String date;
	private float open;
	private float high;
	private float low;
	private float close;
	private int volume;
	private float adjclose;
	private String stock;
	private String timestamp;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getOpen() {
		return open;
	}
	public void setOpen(float open) {
		this.open = open;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public float getClose() {
		return close;
	}
	public void setClose(float close) {
		this.close = close;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public float getAdjclose() {
		return adjclose;
	}
	public void setAdjclose(float adjclose) {
		this.adjclose = adjclose;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
