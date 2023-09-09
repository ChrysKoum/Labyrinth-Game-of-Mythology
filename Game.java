import java.util.Scanner;

//Chrysostomos Koumides  AEM:10148

public class Game {
	int round;
	
	public Game() {
		round=0;
	}
	
	public Game(Game a) {
		round=a.round;
	}
	
	public void setround(int round) {
		this.round=round;
	}
	
	public int getround() {
		return round;
	}
	
	public static char getManualInput(Scanner scanner) {
        char manual;

        do {
            System.out.print("Do you want manual input of the parameters (n, number of supplies, number of rounds) - 'Y' or 'N': ");
            String input = scanner.next();
            manual = input.charAt(0);
            scanner.nextLine(); // Consume the newline character after reading the String
            
            if(manual == 'n'){
                manual = 'N';
            }
            else if (manual == 'y'){
                manual = 'Y';
            }
            
            if (manual != 'Y' && manual != 'N') {
                System.out.println("Please enter 'Y' or 'N'.");
            }
        } while (manual != 'Y' && manual != 'N');

        return manual;
    }
    
    public static int getIntInput(Scanner scanner, String prompt, int minValue, int maxValue) {
        int userInput;

        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                scanner.next(); // Consume invalid input
            }
            userInput = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading the integer

            if (userInput < minValue || userInput > maxValue) {
                System.out.println("Please enter a value between " + minValue + " and " + maxValue + ".");
            }
            
            System.out.println();
        } while (userInput < minValue || userInput > maxValue);

        return userInput;
    }

	public static void main(String[] args) {
        System.out.println("Welcome to the Labyrinth Game!");
        System.out.println("The size of the labyrinth will be n rows and n columns");
        System.out.println();
		Scanner scanner = new Scanner(System.in);

        int size, numberOfSupplies, rounds;
		char manual = getManualInput(scanner);
        
        if (manual == 'Y'){
    		size = getIntInput(scanner, "Enter the size of the labyrinth (n): ", 3, 10);
            numberOfSupplies = getIntInput(scanner, "Enter the number of numberOfSupplies: ", 1, size / 2);
            rounds = getIntInput(scanner, "Enter the number of rounds: ", 1, 500);
        }
        else{
            size = 7;
            numberOfSupplies = 15;
            rounds = 40;
        }
        

		Game game = new Game();
		
		Board b = new Board(size, numberOfSupplies, 0);
		b.setW((b.N*b.N*3+1)/2);
		
		int theseusTile = 0, minotaurTile = (b.N*b.N)/2;
		if (size % 2 == 0){
		    minotaurTile = (b.N*b.N)/2 + b.N/2;
		}
		
		AdvancedPlayer p1 = new AdvancedPlayer(1,"theseus",b,0,0,0);
		Player p2 = new Player(2,"minotaur",b,0,(b.N/2),(b.N/2));

		b.createBoard();
		
		String[][] boardS = new String[2*b.N + 1][b.N];
		int[]idp = new int[4];
		
		while(game.round != rounds) {
			System.out.println("Round of the game is number < "+ game.round +" >");
			boardS = b.getStringRepresentation(theseusTile, minotaurTile);
			
			
			for(int i=0;i<(2*b.N + 1);i++) {   											//Print the (String[][])boardS
				  for(int j=0;j<b.N;j++) {
					  if(j%b.N == 0) {
						  System.out.println();
					  }
					  System.out.print(boardS[i][j]);
				  }
			}
			System.out.println();
			if(game.round % 2 == 0) {
				System.out.println("Theseus turn");
				idp = p1.advancedMove(1, p2);
			}
			else {
				System.out.println("Minotauros turn");
				idp = p2.move(2);
			}
			if(idp[0] == 1) {
				p1.x = idp[1];
				p1.y = idp[2];
			}
			if(idp[0] == 2) {
				p2.x = idp[1];
				p2.y = idp[2];
			}
			
			theseusTile = b.N*p1.x+p1.y;
			minotaurTile = b.N*p2.x+p2.y;
			
			if(p1.score == b.S) {
				System.out.println("Theseus is the winner !! :) !!");
				break;
			}
			if(theseusTile == minotaurTile) {
				  System.out.println("Minotaur eat Theseus .....:(");
				  break;
			}
			if(idp[3] >= 0 && idp[0] == 1) {
				p1.score++;
			}
			game.round++;
		}
		System.out.println();
		System.out.println("--------------The End--------------");
	}

}
