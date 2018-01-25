package application;
	
import java.io.IOException;

import view.HillField;
import view.HillFieldContainer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import model.*;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		
		Map m = new Map();
		m.load("map.txt");
		
		Benchmarker b = new Benchmarker();
		Search s_search = new Search(); 
		EuclidianSearch e_search = new EuclidianSearch(); 
		ChebyshevDistance ch_search = new ChebyshevDistance();
		Search[] SearchObjects = {s_search, e_search, ch_search};
		
		for(int weight = 0; weight <= 5; weight++)
		{
			for(Search so : SearchObjects)
			{
				RunAndBenchmark(so, m, b, weight);
			}
		}
		
		/*
		HillFieldContainer hfc = new HillFieldContainer(m);
		
		
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(hfc);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("PathFinder");
			primaryStage.setWidth(768 + 128);
			primaryStage.setHeight(768 + 16);
			primaryStage.setResizable(false);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	private static void RunAndBenchmark(Search SearchObject, Map m, Benchmarker b, double weight)
	{
		String weightedAplusType = "Uh-oh, negative weight?";
		
		if (weight == 1) { weightedAplusType = "Regular A*"; }
		else if (weight == 0) { weightedAplusType = "GreedyBFS"; }
		else if (weight > 0) { weightedAplusType = "Weighted A*"; }
		
		SearchObject.loadmap(m);
		System.out.println("Running 10 tests on this map with " + 
				weightedAplusType +  ", weight " + weight + "; " + SearchObject.heuristic());
		
		for(int i = 1; i <= 2; i++)
		{
			System.out.println();
			b.reset();
			Coordinate[] sgpair = m.StartGoalPair();
			boolean pathfound = SearchObject.path(sgpair[0], sgpair[1], weight);
			b.end();
			if (!pathfound)
			{
				i--;
				continue;
			}
			
			System.out.println("Test #" + i + " took " + b.msTook() + " ms" + " and " + b.mbTook() + " MB.");
		}
	}
	
}


