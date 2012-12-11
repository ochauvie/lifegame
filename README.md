# LIFE GAME
Ce jeu s'inspire de l'automate cellulaire imaginé par John Horton Conway en 1970.

La grille affiche des cellules qui peuvent se reproduire ou mourrir.
* Une cellule meurt si elle possède moins de 2 voisines ou plus de 3 voisines. Les cellules mortes sont en noire.
* Une cellule née dans une case vide si elle possède exactement 3 voisines. Les nouvelles cellules sont en rouge.

Vous pouvez soit jouer seul avec les cellules vertes, soit jouer contre les cellules mauves.

Des virus sont disponibles pour agir sur le cycle de vie des cellules de la grille. 
Un virus peut:
* Tuer des cellules,
* Doper les naissances des cellules,
* Geler l'évolution des cellules.

Un virus possède une durée de vie et un rayon de propagation.

# Les paramètres du jeu
* Taille de la grille.
* Densité: densité de population de la grille initiale (1 peu peuplée, 10 surpeuplée).
* Temporisation: délai en ms d'affichage des étapes du cycle d'évolution des cellules.
* Difficulté: nombre de virus disponibles.
* Mode du jeu: le jeu peut être en tour par tour pour bien visualiser les étapes de l'évolution, ou en mode automatique.
        