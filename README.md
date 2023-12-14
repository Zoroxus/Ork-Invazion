z# Ork Invasion - 2D Video Game Adaptation of Warhammer 40.000 10th Edition
# Projet réalisée par Maxime Pouleyn,Olivier Lohez et Yann Wojciechowski
***

## Sommaire  
* Présentation du projet
  * Commande
  * Début de jeu
  * Configuration pour le dev
  * Construire et éxécuter le jeu
  * Construire le jeu pour le déploiement
* Partie technique
  * Player
  * Arme
  * Unite
  * Map 
  * Où rajouter des classes ?

## Présentation du projet

Notre projet est un jeu en tour par tour où deux joueurs viendront s'affronter avec pour l'instants une armée de SpaceMarines et une armée d'Orks.
L'armée Orks est composé de Boyz,Nobz et Warboss équipait avec des armes comme le Choppa,Shoota,Power_klaw,Slugga et le Twin_Slugga.
L'armée SpaceMarines est composé d'Intercessor équipait avec des armes comme le Bolt_Rifle et Close_Combat_Weapon

## Commande

### Touches générales

Clique gauche (seul) - sélection de base  
Clique avec une unité selectionné - bouger  
Clique droit - Attaquer  
Touches directionnelles - Bouger le curseur  
P - Ramasser arme à terre  
Entrée - Selectionner case  
M - Bouger  
Espace - Passer le tour  

### Touches Easter Egg 
Ctrl + D - Debug Mode  
Ctrl + K - Knuckles Mode  

### Touches Debug

L - Tuer l'unité selectionnée  
C - Réduire l'unité de 1 membre  
V - Augmenter l'unité de 1 membre  

## Début de jeu
Au démarrage du jeu , vous arrivez sur une map générer aléatoirement rendant chaque partie différent.
Vous permettant de vivre une expérience unique à chaque partie.  

![T-JAV-501-LIL-2](/ReadmeScreen/Affichage_debut_de_partie.png)

Vous verrez dans le plateau divers blocs indiquant des éléments comme le bloc d'herbe :  
![T-JAV-501-LIL-2](/ReadmeScreen/Herbe.png)  
Dans cette case , vous n'avez aucun malus. Vous pouvez marcher dessus et tirer à travers sans problème.

La zone rocheuse :  
![T-JAV-501-LIL-2](/ReadmeScreen/Roche.png)  
Dans cette case, vous ne pouvez ni tirer à travers ni marcher à travers.

Le Lac:  
![T-JAV-501-LIL-2](/ReadmeScreen/Eau.png)  
Dans cette case, vous ne pouvez pas passer à travers mais vous pouvez tirer à travers cette case.  

Et pour terminer , nous sommes dans une zone de guerre donc il y a des batîments détruits dans la zone de combat:  
![T-JAV-501-LIL-2](/ReadmeScreen/Batiment.png)  
Dans cette case, on ne peut ni tirer sur l'ennemi ni marcher dessus.  

À droite vous pourrez voir l'apparence du personnage puis son nom et ses différentes statistiques:  
![T-JAV-501-LIL-2](/ReadmeScreen/Stats_Unite.png)  
- M = Mouvement(Movement)  
- T = Endurance(Toughness)  
- Sv = Sauvegarde(save)  
- W = Point de vie(Wounds)  
- Nb = Nombre(Number)  
- Arme de mélée  
- Arme de distance  
- Experience  

Vous pouvez quand vous abattez un ennemi récupérer une arme au sol.

## Gameplay 

Quand vous jouez , vous viendrez jouer les SpaceMarines se trouvant sur la droite de l'écran , ils pourront bouger et tirer.
Quand vous cliquez sur un SpaceMarines , vous pouvez voir les différentes cases où il peut aller:

![T-JAV-501-LIL-2](/ReadmeScreen/Mouvement_Intercessors.png)

Et quand vous serez assez prêt de vos ennemis , vous pourrez les attaquer :

![T-JAV-501-LIL-2](/ReadmeScreen/Attaque_Intercessors.png)

## Configuration pour le dev

LibGDX
Visual Studio Code Extension : Coding Pack for Java

### Construire et éxecuter le jeu :
```bash
./gradlew desktop:run
```

### Construire le jeu pour le déploiement :
```bash
./gradlew desktop:dist
```

## Partie technique 
À partir de cette instant , on va parler plus techniquement du projet.
Le projet a était réalisée avec Java et le framework LibGDX.
Nous nous sommes appuyer du livre de règles de Warhammer 40K 10th édition pour avoir nos règles que nous avons adapter pour correspondre à nos attentes.  
Vous pourrez voir dans ce lien , notre diagramme de classe (et aussi nos schémas pour la réalisation du projet).  
https://lucid.app/lucidchart/14d1bdee-13f9-46ae-ae0c-510e928c7bb4/edit?invitationId=inv_3d888d56-0320-48b5-9f07-97146bd28e17

Mais sinon on peut compter trois grandes classes :
- Player
- Arme
- Unite
- Map

## Player
La classe Player permet au joueur d'avoir ; 
- String name : nom du joueur
- Armee armee : armée du joueur
  
La classe Armee quand à elle dispose : 
- String name : Nom de l'armée
- String faction : faction de l'armée
- list<? extends Unite> army : On viendra mettre les différentes Unite de l'armée

On peut aussi avoir une classe Bot qui permettra au joueur d'avoir un adversaire :
- tileData[] route : permet d'avoir les routes vers l'ennemi pour le bot
- Map map : permet à l'ordinateur de récupérer toute la map
- ArrayList<tileData> allUnits : récupère toutes les Unite sur la map
- ArrayList<tileData> allEnemy : récupère tous les Unite ennemi sur la map

## Arme
La classe Arme est une classe Abstraite qui va nous permettre de créer les futurs armes, elle contient en attribut :
- int portee : portee de l'arme
- String attack : attaque de l'arme
- int ct : capacite de tir de l'arme
- int strength : force de l'arme
- int pa : pénétration d'armure
- String dmg : dommage de l'arme
- boolean ismelee : pour déterminer si l'arme est une arme de mêlée ou de distance


## Unite
La classe Unite est une classe Abstraite qui va nous permettre de créer les futurs unités, elle contient en attribut :
- int movement : mouvement de l'unite
- int endurance : endurance de l'unite
- int save : capacité de sauvegarde de l'unite
- int pv : points de vie de l'unite
- int commandement : commandement de l'unite
- int capture_capacity : capacite de capture de l'unite
- int number : nombre d'unite
- int exp : expérience de l'unite
- Arme melee : arme de mêlée actuellement équiper
- Arme distance : arme de distance actuellement équiper
- Player owner : propriétaire de l'unité
- Player controller : controlleur de l'unité

## Map
La classe Map contient en attribut :  
- TileData[][] mapData : contient les informations par rapport au différentes cases du plateau 
- Decors decors : permet de faire le décors autour de la zone de plateau 
- int sizeX : définit la taille maximum en abscisse
- int sizeY : définit la taille maximum en ordonnée
  
La classe TileData contient en attribut:   
- int x : récupère la position abscisse
- int y : récupère la position ordonnée
- tileType type : récupére les différentes informations par rapport à la case
- Unite unit : permet de savoir si la case contient une unité.  
- Arme arme : permet de savoir quand à elle de savoir s'il y a une arme tomber au sol.

La classe tileType contient en attribut:  
- float color : qui vient déterminer la couleur de la case  
- boolean obscurant : permet de savoir si la case bloque la vue  
- boolean passable : permet de savoir si la case est passable par les unités  

## Où rajouter des classes ?

`./core/src/com/orkin/game`
(there is already the main class, OrkIn)
