import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class player extends Thread {
	private String name;
	private map playerMap;
	private map enemyMap;
	public boolean playerTurn;
	private player enemy; ///
	transient int roundsLeft;

	public void Name(String n) {
		this.name = n;
	}

	public String getPlayerName() {
		return this.name;
	}
	
	public void setPlayerMap(map m) {
		this.playerMap = m;
	}

	public void setEnemy(player e) ///
	{
		this.enemy = e;
	}

	public void setEnemyMap(map m) {
		this.enemyMap = m;
	}

	/*
	 * public void run() { placeShip(); attackShip(); }
	 */
	public map getPlayerMap() {
		return this.playerMap;
	}

	public void placeShip() {
		try {
			this.playerMap.semaphore.acquire();
			while (!playerTurn) {
			} ///
			System.out.println(name + "Please place a ship down in the floowing format:");
			System.out.println("vertical position [space] horizontal position");
			System.out.println(name + "e.g. a 0");
			boolean validInput = false;
			while (validInput == false) {
				try {
					Scanner scan = new Scanner(System.in);
					String input = scan.nextLine();
					String[] inputArray = input.split(" ");
					if (inputArray.length != 2)
						throw new InputMismatchException();
					if (inputArray[0].length() != 1)
						throw new InputMismatchException();
					if (!Character.isLetter(inputArray[0].charAt(0)))
						throw new InputMismatchException();
					char rawVer = inputArray[0].charAt(0);
					int ver = rawVer - 97;
					int hor = Integer.valueOf(inputArray[1]);
					// check if position out of map
					if (ver >= this.playerMap.getHeight() || hor >= this.playerMap.getWidth())
						throw new IndexOutOfBoundsException();
					// place ship on map
					if (!playerMap.isOccupied(ver, hor)) {
						playerMap.occupy(ver, hor);
						System.out.println("Successful placement!");
					} else
						throw new IllegalArgumentException();
					playerTurn = false; ///
					enemy.playerTurn = true; ///
					validInput = true;
				} catch (IndexOutOfBoundsException e) {
					System.out.println("input out of map");
				} catch (InputMismatchException e1) {
					System.out.println("input doesn't match the required format");
				} catch (NumberFormatException e1) {
					System.out.println("input doesn't match the required format");
				} catch (IllegalArgumentException e1) {
					System.out.println("position already occupied");
				}
			}
		} catch (InterruptedException e2) {
			System.out.println("interruption during placing ship");
		} finally {
			this.playerMap.semaphore.release();
		}
	}

	public void attackShip() {
		try {
			this.playerMap.semaphore.acquire();
			while (!playerTurn) {
			} ///
			System.out.println(name + "Please try to attack a ship down in the floowing format:");
			System.out.println("vertical position [space] horizontal position");
			System.out.println(name + "e.g. a 0");
			boolean validInput = false;
			while (validInput == false) {
				try {
					Scanner scan = new Scanner(System.in);
					String input = scan.nextLine();
					String[] inputArray = input.split(" ");
					if (inputArray.length != 2)
						throw new InputMismatchException();
					if (inputArray[0].length() != 1)
						throw new InputMismatchException();
					if (!Character.isLetter(inputArray[0].charAt(0)))
						throw new InputMismatchException();
					char rawVer = inputArray[0].charAt(0);
					int ver = rawVer - 97;
					int hor = Integer.valueOf(inputArray[1]);
					// check if position out of map
					if (ver >= this.playerMap.getHeight() || hor >= this.playerMap.getWidth())
						throw new IndexOutOfBoundsException();
					if (enemyMap.isOccupied(ver, hor)) {
						enemyMap.remove(ver, hor);
						System.out.println("Successful attack!");
					} else
						System.out.println("attack unsuccessful!");
					playerTurn = false; ///
					enemy.playerTurn = true; ///
					validInput = true;
				} catch (IndexOutOfBoundsException e) {
					System.out.println("input out of bound or position already occupied");
				} catch (InputMismatchException e1) {
					System.out.println("input mismatch");
				}
			}
		} catch (InterruptedException e2) {
			System.out.println("interruption during attacking ship");
		} finally {
			this.playerMap.semaphore.release();
		}
	}

	public String attackShip(String guess) throws Exception {

		String[] inputArray = guess.split(" ");
		char rawVer = inputArray[0].charAt(0);
		int ver = rawVer - 97;
		int hor = Integer.valueOf(inputArray[1]);
		// check if position out of map
		//if (ver >= this.playerMap.getHeight() || hor >= this.playerMap.getWidth())
		if (enemyMap.isOccupied(ver, hor)) {
			enemyMap.remove(ver, hor);
			return "Successful attack!";
		} else {
			return "attack unsuccessful!";
		}
			
	}

}
