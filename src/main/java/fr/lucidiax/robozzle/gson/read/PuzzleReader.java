package fr.lucidiax.robozzle.gson.read;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.lucidiax.robozzle.play.stuff.Puzzle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PuzzleReader {

    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Puzzle.class, new PuzzleDeserializer()).create();

    private File filePuzzle;

    public PuzzleReader(File filePuzzle){
        this.filePuzzle = filePuzzle;
    }

    public Puzzle read(){
        String raw = "";
        try {
            raw = new String(Files.readAllBytes(Paths.get(filePuzzle.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return GSON.fromJson(raw, Puzzle.class);
    }

}
