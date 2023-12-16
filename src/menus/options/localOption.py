from menus.options.abstractOption import abstractOption
from menus.characterMenu import characterMenu
from players.humanPlayer import humanPlayer
from model.battle import battle

class localOption(abstractOption):
    def __init__(self, gameObj, parentMenu) -> None:
        super().__init__("Local Multiplayer", parentMenu)
        self.__gameObj = gameObj
        
    def handler(self):
        self.__gameObj.setOpponent(humanPlayer(self.__gameObj))
        characterSelectionMenu = characterMenu(self.getOwner(), self.__gameObj, 0)
        
        self.__gameObj.setCurrentMenu(characterSelectionMenu)
        self.__gameObj.setBattle(battle(self.__gameObj, "assets/images/backgrounds/backbackground.jpg",None))
        characterSelectionMenu.runSelection()