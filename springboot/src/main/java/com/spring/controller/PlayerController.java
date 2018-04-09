package com.spring.controller;

import com.google.gson.Gson;
import com.spring.map.CompleteMap;
import com.spring.player.Player;
import com.spring.repository.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    private   Gson gson = new Gson();
    @RequestMapping(value="user/create", method = RequestMethod.POST)
    public void addPlayerInDatabase(@RequestBody String playerInJson){
        System.out.println(playerInJson);
        playerService.addPlayer(gson.fromJson(playerInJson, Player.class));
    }

    @RequestMapping(value="user/update", method = RequestMethod.POST)
    public void updatePlayerInDatabase(@RequestBody String playerInJson){
        playerService.updatePlayer(gson.fromJson(playerInJson, Player.class));

    }
    @RequestMapping(value="user/delete", method = RequestMethod.POST)
    public void deletePlayerInDatabase(@RequestBody String playerInJson){
        playerService.deletePlayer(gson.fromJson(playerInJson, Player.class).getUID());

    }
    @RequestMapping(value="user/get", method = RequestMethod.POST)
    public Player getPlayerInDatabase(@RequestBody String playerInJson){
        return playerService.getPlayer(gson.fromJson(playerInJson, Player.class).getUID());

    }
    @RequestMapping(value="user/play", method = RequestMethod.POST)
    public List<Player> getPlayerToPlay(@RequestBody String playerInJson){
        System.out.println("CAKE AND GLORY HERE: " + playerInJson);
        if(playerService.getPlayersToPlay(gson.fromJson(playerInJson, Player.class).getUID()).isPresent()) {
            Player player1 = playerService.getPlayersToPlay(gson.fromJson(playerInJson, Player.class).getUID()).get().get(0);
            Player player2 = playerService.getPlayersToPlay(gson.fromJson(playerInJson, Player.class).getUID()).get().get(1);
            if(player1.getMap() == null) {
                CompleteMap completeMap = new CompleteMap();
                String map = completeMap.getJsonInTiledFormatWithDataInserted();
                player1.setMap(map);
                player2.setMap(map);
                playerService.updatePlayer(player1);
                playerService.updatePlayer(player2);
            }
            return Arrays.asList(player1, player2);
        }
        System.out.println("cake 12" + Arrays.asList(new Player(), new Player()));
        return Arrays.asList(new Player(), new Player());

    }
    @RequestMapping(value="user/getAllPlayers")
    public List<Player> getAllPlayers(){
        return playerService.getAllPlayers();

    }
    @RequestMapping(value="user/getAll")
    public String getAll(){
        return playerService.getAll();

    }


}
