package tv.fussel.staff;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kong.unirest.core.Unirest;
import tv.fussel.StaffWebhook;
import tv.fussel.handler.ChangeEntry;
import tv.fussel.handler.ChangeType;

import java.util.*;

public class StaffList {
    private List<Player> staffList;
    private List<Player> oldStaffList;
    private List<ChangeEntry> changeList;
    private String apiData;

    public StaffList() {
        update();
        oldStaffList = staffList;
    }

    public void test() {
        apiData = "{\"groups\":[{\"group\":\"OWNER\",\"players\":[{\"uuid\":\"5dd7513c-f66e-483f-9735-5a1c22e61ce2\",\"playerName\":\"Maaxxxii\",\"playerNameColor\":\"LIGHT_PURPLE\"},{\"uuid\":\"0175212e-c38a-42c6-8e49-ef69592f54a1\",\"playerName\":\"6k2\",\"playerNameColor\":\"DARK_RED\"},{\"uuid\":\"7331dcae-7ca7-4836-b1e8-8662512195ee\",\"playerName\":\"Fypsilon\",\"playerNameColor\":\"DARK_RED\"},{\"uuid\":\"4101eb86-ad14-429f-a7e8-8ee0a1db8ee1\",\"playerName\":\"pxscxl\",\"playerNameColor\":\"DARK_RED\"}]},{\"group\":\"HEAD_DEVELOPER\",\"players\":[{\"uuid\":\"0f926af9-9195-4a83-9029-385403e568d0\",\"playerName\":\"Gabik21\",\"playerNameColor\":\"GOLD\"}]},{\"group\":\"HEAD_ADMINISTRATOR\",\"players\":[{\"uuid\":\"909e6e8a-693e-4b83-9694-2b4f6e125aa6\",\"playerName\":\"Depresxion\",\"playerNameColor\":\"AQUA\"},{\"uuid\":\"7b6bfb55-3b45-4a85-932e-5ab32a1495d3\",\"playerName\":\"Truston\",\"playerNameColor\":\"DARK_RED\"}]},{\"group\":\"DEVELOPER\",\"players\":[{\"uuid\":\"7d3df092-6f6b-4ef0-8d8b-396d17b0e2a5\",\"playerName\":\"Stepes\",\"playerNameColor\":\"DARK_RED\"},{\"uuid\":\"4e2c0348-ef70-42a2-b2a9-12c338d333c6\",\"playerName\":\"DerRedstoner\",\"playerNameColor\":\"RED\"},{\"uuid\":\"148f1abc-6352-41fa-9c91-f666c3b04082\",\"playerName\":\"EnzoL_\",\"playerNameColor\":\"LIGHT_PURPLE\"}]},{\"group\":\"ADMINISTRATOR\",\"players\":[{\"uuid\":\"e01bfc2a-e084-4885-a5a4-e2bb745f1411\",\"playerName\":\"2fach\",\"playerNameColor\":\"RED\"},{\"uuid\":\"dfae338d-6eb0-4487-ad75-f2e49820bf50\",\"playerName\":\"Lucaarooo\",\"playerNameColor\":\"DARK_RED\"},{\"uuid\":\"d365d9cf-8234-454e-9107-9dcd5f69627c\",\"playerName\":\"kuyso\",\"playerNameColor\":\"RED\"}]},{\"group\":\"SENIOR_MODERATOR\",\"players\":[{\"uuid\":\"331176ff-6df2-4121-af84-786fe1727080\",\"playerName\":\"ItsKodii\",\"playerNameColor\":\"AQUA\"},{\"uuid\":\"7a983017-5f3e-4550-af6f-21fec09fe1c4\",\"playerName\":\"zodah\",\"playerNameColor\":\"DARK_GREEN\"},{\"uuid\":\"89bf9069-f3ba-4523-a0a7-cddd9495a3bc\",\"playerName\":\"Baackk\",\"playerNameColor\":\"GOLD\"},{\"uuid\":\"6ae60021-2572-44ce-a8cf-ef82fcc59d7d\",\"playerName\":\"Fizc\",\"playerNameColor\":\"DARK_PURPLE\"},{\"uuid\":\"3a321bb8-f42f-419b-9edf-1b359350521b\",\"playerName\":\"realLuka\",\"playerNameColor\":\"DARK_PURPLE\"},{\"uuid\":\"56d2f4ea-0331-4f25-be0b-cae371da8591\",\"playerName\":\"FusselTV\",\"playerNameColor\":\"GRAY\"}]},{\"group\":\"MODERATOR\",\"players\":[{\"uuid\":\"767689ca-3c02-48e6-ad91-1655dc459c89\",\"playerName\":\"Dionyo\",\"playerNameColor\":\"DARK_AQUA\"},{\"uuid\":\"b4c3987f-a2c4-450e-a0bf-34ea59f66595\",\"playerName\":\"Evxn101\",\"playerNameColor\":\"YELLOW\"},{\"uuid\":\"64e610ce-0717-4754-ab0e-ab40cfcf4cfc\",\"playerName\":\"JxstHillex\",\"playerNameColor\":\"DARK_AQUA\"},{\"uuid\":\"888bce67-21d7-4701-bec0-f1045a9873c0\",\"playerName\":\"LaSsyMC\",\"playerNameColor\":\"BLUE\"},{\"uuid\":\"52ddfe56-5525-415c-a851-5a8b29653fa5\",\"playerName\":\"sixelabruti\",\"playerNameColor\":\"LIGHT_PURPLE\"},{\"uuid\":\"ad518e10-a19f-4f34-bb0d-1e90474043be\",\"playerName\":\"Anixc\",\"playerNameColor\":\"DARK_AQUA\"},{\"uuid\":\"8342e4d0-f00d-4a7f-95c4-80fc55b94d1d\",\"playerName\":\"Dachston\",\"playerNameColor\":\"WHITE\"},{\"uuid\":\"2137195e-f96b-4daa-8eb2-ee80d43b51ee\",\"playerName\":\"alenaaa\",\"playerNameColor\":\"LIGHT_PURPLE\"}]},{\"group\":\"TRIAL_MODERATOR\",\"players\":[{\"uuid\":\"ec0467bf-1053-41c8-9871-dcd0b336eb25\",\"playerName\":\"Deum\",\"playerNameColor\":\"WHITE\"},{\"uuid\":\"44a8364a-b105-4747-afb1-bed62ea78885\",\"playerName\":\"Faantum\",\"playerNameColor\":\"DARK_RED\"},{\"uuid\":\"456192b4-0601-44ca-8777-139b171c4c3b\",\"playerName\":\"Blastingg\",\"playerNameColor\":\"AQUA\"}]}]}";
        oldStaffList = staffList;
        staffList = mapApiData(apiData);
    }

    public void update() {
        updateApiData();

        if (staffList != null) oldStaffList = staffList;

        if (apiData == null) return;

        staffList = mapApiData(apiData);

        StaffWebhook.LOGGER.info("Updated " + staffList.size() + " Staff Members");
    }

    public void clearChanges() {
        this.changeList.clear();
    }

    private boolean staffListChanged() {
        return oldStaffList != staffList;
    }

    private Player getPlayerByUUID(List<Player> players, UUID uuid) {
        Optional<Player> playerOptional = players.stream()
                .filter(player -> player.uuid().equals(uuid))
                .findFirst();

        return playerOptional.orElse(null);
    }

    private void addJoined() {
        staffList.forEach(newPlayer -> {
            //schaue in der alten stafflist, ob die uuids des spielers in der neuen staff list vorhanden ist
            if (getPlayerByUUID(oldStaffList, newPlayer.uuid()) == null) {
                //da der neue spieler nicht in der alten Liste gefunden wurde, muss er gejoined sein
                ChangeEntry changeEntry = new ChangeEntry(newPlayer, newPlayer, ChangeType.JOIN);
                StaffWebhook.LOGGER.info("Der Staff [" + newPlayer.rank().name() + "] " + newPlayer.username() + " ist neu im Team!");

                changeList.add(changeEntry);
            }
        });
    }

    private void addLeft() {
        oldStaffList.forEach(oldPlayer -> {
            //schaue in der neuen stafflist, ob die uuids des spielers in der alten staff list vorhanden ist
            if (getPlayerByUUID(staffList, oldPlayer.uuid()) == null) {
                //da der alte spieler nicht in der neuen Liste gefunden wurde, muss er geleaved sein
                ChangeEntry changeEntry = new ChangeEntry(oldPlayer, oldPlayer, ChangeType.LEFT);
                StaffWebhook.LOGGER.info("Der Staff [" + oldPlayer.rank().name() + "] " + oldPlayer.username() + " ist nicht mehr im Team!");

                changeList.add(changeEntry);
            }
        });
    }

    private void addRankChanges() {
        oldStaffList.forEach(oldPlayer -> {
            Player newPlayer = getPlayerByUUID(staffList, oldPlayer.uuid());

            //when player is not in both lists
            if (newPlayer == null) return;

            //when there is no rank change
            if (Objects.equals(oldPlayer.rank().name(), newPlayer.rank().name())) return;

            StaffWebhook.LOGGER.info("Der rang von [" + oldPlayer.rank().name() + "] " + oldPlayer.username() + " hat sich zu [" + newPlayer.rank().name() + "] " + newPlayer.username() + " geändert!");

            ChangeEntry changeEntry = new ChangeEntry(oldPlayer, newPlayer, ChangeType.PROMOTION);

            //when the old rank was higher than the new rank
            if (oldPlayer.rank().level() > newPlayer.rank().level()) {
                changeEntry = new ChangeEntry(oldPlayer, newPlayer, ChangeType.DEMOTION);
            }

            changeList.add(changeEntry);
        });
    }

    public List<ChangeEntry> getStaffChanges() {
        if (staffList == null) return null;
        //return null when there are no changes
        if (!staffListChanged()) {
            return null;
        }

        changeList = new ArrayList<>();

        addJoined();
        addLeft();
        addRankChanges();

        return changeList;
    }

    public List<Player> getPlayers(String rankName) {
        if (staffList == null) return null;

        return staffList.stream()
                .filter(player -> Objects.equals(player.rank().name().toLowerCase(), rankName.toLowerCase()))
                .toList();
    }

    private void updateApiData() {
        try {
            apiData = Unirest
                    .get("https://api.pvpgym.net/playerlist/staff")
                    .header("Accept", "application/json")
                    .asJson()
                    .getBody()
                    .toString();
            StaffWebhook.LOGGER.info("Fetched api data");
        } catch (Exception e) {
            StaffWebhook.LOGGER.error("Failed to get api data!", e);
            apiData = "";
        }

    }

    private List<Player> mapApiData(String apiData) {
        List<Player> newStaffList = new ArrayList<>();
        JsonObject jsonObject = new Gson().fromJson(apiData, JsonObject.class);
        JsonArray groupsArray = jsonObject.getAsJsonArray("groups");

        int highestLevel = groupsArray.size(); // Highest level for the first group

        for (JsonElement groupElement : groupsArray) {
            JsonObject groupObject = groupElement.getAsJsonObject();
            String groupName = groupObject.get("group").getAsString();
            JsonArray playersArray = groupObject.getAsJsonArray("players");

            for (JsonElement playerElement : playersArray) {
                JsonObject playerObject = playerElement.getAsJsonObject();
                String uuid = playerObject.get("uuid").getAsString();
                String playerName = playerObject.get("playerName").getAsString();

                Rank rank = new Rank(groupName, "todo", highestLevel);
                Player player = new Player(UUID.fromString(uuid), playerName, rank);

                newStaffList.add(player);
            }

            highestLevel--; // Decrement the level for the next group
        }
        return newStaffList;
    }
}
