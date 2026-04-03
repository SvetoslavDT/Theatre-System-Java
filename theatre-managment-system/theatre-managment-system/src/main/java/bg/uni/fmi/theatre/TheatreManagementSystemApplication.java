package bg.uni.fmi.theatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheatreManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheatreManagementSystemApplication.class, args);
	}

//    private ShowService showService;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        System.out.println("🚀 Application started successfully!");
//
//        // Add Shows
//        showService.addShow("Hamlet", "Drama", 180);
//        showService.addShow("The Nutcracker", "Ballet", 120);
//
//        try {
//            showService.addShow("", "Comedy", 90);
//        } catch (IllegalArgumentException ex) {
//            System.out.println(ex);
//        }
//
//        System.out.println("---------------------------------------");
//        System.out.println("✅ Shows added successfully!");
//        System.out.println("---------------------------------------");
//
//        // Display All Shows
//        System.out.println("📌 Displaying all shows:");
//        showController.displayAllShows();
//        System.out.println("---------------------------------------");
//
//        System.out.println("🔄 Updating 'Hamlet' duration to 200 minutes...");
//        showController.updateShow(1, "Hamlet", "Drama", 200);
//
//        System.out.println("---------------------------------------");
//        System.out.println("📌 Displaying updated shows:");
//        showController.displayAllShows();
//
//        System.out.println("---------------------------------------");
//        System.out.println("📌 Displaying all shows by genre 'Drama':");
//        List<Show> dramaShows = showController.getShowsByGenre("Drama");
//        dramaShows.stream().forEach(System.out::println);
//        System.out.println("---------------------------------------");
//    }
}
