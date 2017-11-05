package edu.umass.mterpstra;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.exceptions.IllegalValueException;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cube;
import eu.printingin3d.javascad.utils.SaveScadFiles;


public class Piece
{
	String filename;
	List<Vector3> cubes = new ArrayList<>();
	Abstract3dModel onePiece;
	public Piece(Vector3 cc, String filename)
	{
		this.filename = filename;
		cubes.add(cc);
	}

	public boolean add(Vector3 cc)
	{
		if(cc == null) return false;

		cubes.add(cc);
		return true;
	}
	
	public boolean add(Piece p)
	{
		for(Vector3 c1 : cubes)
		{
			for(Vector3 c2 : p.cubes)
			{
				if(isNeighbor(c1, c2))
				{
					cubes.addAll(p.cubes);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isNeighbor(Vector3 c1, Vector3 c2)
	{
		int xDif = Math.abs(c1.x - c2.x);
		int yDif = Math.abs(c1.y - c2.y);
		int zDif = Math.abs(c1.z - c2.z);
		
		return xDif + yDif + zDif == 1;
	}

	public int size()
	{
		return cubes.size();
	}
	
	public void generateShape()
	{
		Abstract3dModel[] cube = new Cube[cubes.size()];
		
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int minZ = Integer.MAX_VALUE;
		
		for(int i = 0; i < cube.length; i++)
		{
			minX = Math.min(cubes.get(i).x, minX);
			minY = Math.min(cubes.get(i).y, minY);
			minZ = Math.min(cubes.get(i).z, minZ);
		}
		
		for(int i = 0; i < cubes.size(); i++)
		{
			cubes.set(i, new Vector3(cubes.get(i).x - minX, cubes.get(i).y - minY, cubes.get(i).z - minZ));
		}
		
		for(int i = 0; i < cube.length; i++)
		{
			Abstract3dModel c = new Cube(1);
			c = c.move(new Coords3d(cubes.get(i).x, cubes.get(i).y, cubes.get(i).z));
			cube[i] = c;
		}
		
		onePiece = cube[0];
		for(int i = 1; i < cube.length; i++)
		{
			onePiece = onePiece.addModel(cube[i]);
		}
	}

	public void save(int i)
	{
		try
		{
			new SaveScadFiles(new File(filename)).addModel("piece_" + (i + 1) + ".scad", onePiece).saveScadFiles();
		} 
		catch (IllegalValueException | IOException e)
		{
			e.printStackTrace();
		}
	}
}
