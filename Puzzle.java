package edu.umass.mterpstra;

import java.util.ArrayList;
import java.util.List;

public class Puzzle 
{
	public static void generate(int maxPieces, int minPieces, int length, String filename)
	{
		List<Piece> pieces = new ArrayList<>();
		while(pieces.size() < minPieces || pieces.size() > maxPieces)
		{
			pieces = getPuzzle(maxPieces, minPieces, length, filename);
		}
		
		for(int i = 0; i < pieces.size(); i++)
		{
			pieces.get(i).generateShape();
			pieces.get(i).save(i);
		}
	}
	
	public static List<Piece> getPuzzle(int maxPieces, int minPieces, int length, String filename)
	{

		Vector3[][][] cubes = new Vector3[length][length][length];
		ArrayList<Piece> pieces = new ArrayList<>();

		for(int x = 0; x < length; x++)
		{
			for(int y = 0; y < length; y++)
			{
				for(int z = 0; z < length; z++)
				{
					Vector3 cu = new Vector3(x, y, z);
					cubes[x][y][z] = cu;
				}
			}
		}

		for(int x = 0; x < length; x++)
		{
			for(int y = 0; y < length; y++)
			{
				for(int z = 0; z < length; z++)
				{
					if(Math.random() < (maxPieces + minPieces / 2.0)/(length*length*length) && cubes[x][y][z] != null)
					{

						Piece p = new Piece(cubes[x][y][z], filename);
						cubes[x][y][z] = null;

						pieces.add(p);
					}
				}
			}
		}

		boolean stillAdding = true;
		while(stillAdding)
		{
			stillAdding = false;
			for(int i = 0; i < pieces.size(); i++)
			{
				List<Integer[]> neighbors = findNeighbors(pieces.get(i), cubes, length);

				if(neighbors.size() > 0)
				{
					stillAdding = true;
					Integer[] coord = neighbors.get((int) Math.random() * neighbors.size());

					pieces.get(i).add(cubes[coord[0]][coord[1]][coord[2]]);
					cubes[coord[0]][coord[1]][coord[2]] = null;
				}
			}
		}
		return pieces;
	}

	public static List<Integer[]> findNeighbors(Piece p, Vector3[][][] cubes, int length)
	{
		List<Integer[]> intList = new ArrayList<>();
		for(Vector3 c : p.cubes)
		{
			int tempX = c.x;
			int tempY = c.y;
			int tempZ = c.z;

			if(tempX > 0 && tempX + 1 < length && cubes[tempX + 1][tempY][tempZ] != null) intList.add(new Integer[] {tempX + 1, tempY, tempZ});
			if(tempX > 0 && tempX + 1 < length && cubes[tempX - 1][tempY][tempZ] != null) intList.add(new Integer[] {tempX - 1, tempY, tempZ});

			if(tempY > 0 && tempY + 1 < length && cubes[tempX][tempY + 1][tempZ] != null) intList.add(new Integer[] {tempX, tempY + 1, tempZ});
			if(tempY > 0 && tempY + 1 < length && cubes[tempX][tempY - 1][tempZ] != null) intList.add(new Integer[] {tempX, tempY - 1, tempZ});

			if(tempZ > 0 && tempZ + 1 < length && cubes[tempX][tempY][tempZ + 1] != null) intList.add(new Integer[] {tempX, tempY, tempZ + 1});
			if(tempZ > 0 && tempZ + 1 < length && cubes[tempX][tempY][tempZ - 1] != null) intList.add(new Integer[] {tempX, tempY, tempZ - 1});
		}

		return intList;
	}
}


/*
cubes[x][y][z] = null;
int tempX = x;
int tempY = y;
int tempZ = z;
int counter = 0;
int override= 0;

while(override < 1000)
{
	int sign = 1;
	if(Math.random() > .5) sign *= -1;

	double dir = Math.random();
	if(dir < .33) tempX += sign;
	else if(dir < .66) tempY += sign;
	else tempZ += sign;

	if(tempZ < length && tempZ > 0 && tempX < length && tempX > 0 && tempY < length && tempY > 0 && p.add(cubes[tempX][tempY][tempZ]))
	{
		cubes[tempX][tempY][tempZ] = null;
	}
	else
	{
		if(dir < .33) tempX -= sign;
		else if(dir < .66) tempY -= sign;
		else tempZ -= sign;

		counter++;
		if(counter > 10)
		{
			override++;
			counter = 0;
			tempX = x;
			tempY = y;
			tempZ = z;
		}
	}
}

pieces.add(p);
 */
