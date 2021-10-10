package by.epam.task6001.dao.impl;

import by.epam.task6001.dao.DAOException;
import by.epam.task6001.dao.BookIdDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileBookIdDAO implements BookIdDAO {
    private final String idFileName;

    {
        idFileName= FileBookIdDAO.class
                .getResource("/by/epam/task6001/resource/bookId.txt")
                .toString()
                .substring(6);
    }
    @Override
    public long getNextId() throws DAOException {
        long nextId;

        nextId=readCurrentId()+1;
        writeNextId(nextId);

        return nextId;
    }

    private long readCurrentId() throws DAOException {
        long currentId;
        String currentIdString;
        String idRegex;

        idRegex="[1-9][0-9]*";

        try(BufferedReader reader = new BufferedReader(new FileReader(idFileName))){

            if(reader.ready()){
                currentIdString=reader.readLine();
                reader.close();

                if(currentIdString.matches(idRegex)){
                    currentId=Long.parseLong(currentIdString);
                    return currentId;
                }

            }

            throw new DAOException("Incorrect data in file bookId!");

        } catch(IOException ioException){
            throw new DAOException(ioException);
        }
    }

    private void writeNextId(long nextId) throws DAOException {
        try(FileWriter fileWriter = new FileWriter(idFileName)){

            fileWriter.write(Long.toString(nextId));

        } catch(IOException ioException){
            throw new DAOException(ioException);
        }
    }
}
