module ChatApp {
	
	requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires java.desktop;
	requires java.xml;
	requires javafx.graphics;
	requires javafx.base;
	requires com.fasterxml.jackson.databind;
	//requires jakarta.xml.bind;
	requires java.xml.bind;
	requires javafx.media;
	
	
	opens controller to javafx.fxml;
	opens model to javafx.fxml;
	
	exports controller;
	exports application;
	exports model;
	
	opens application to javafx.graphics;
	opens client to java.xml.bind;
}
