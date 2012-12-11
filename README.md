# LIFE GAME
Ce jeu s'inspire de l'automate cellulaire imagin� par John Horton Conway en 1970.

La grille affiche des cellules qui peuvent se reproduire ou mourrir.
* Une cellule meurt si elle poss�de moins de 2 voisines ou plus de 3 voisines. Les cellules mortes sont en noire.
* Une cellule n�e dans une case vide si elle poss�de exactement 3 voisines. Les nouvelles cellules sont en rouge.

Vous pouvez soit jouer seul avec les cellules vertes, soit jouer contre les cellules mauves.

Des virus sont disponibles pour agir sur le cycle de vie des cellules de la grille. 
Un virus peut:
* Tuer des cellules,
* Doper les naissances des cellules,
* Geler l'�volution des cellules.

Un virus poss�de une dur�e de vie et un rayon de propagation.

# Les param�tres du jeu
* Taille de la grille.
* Densit�: densit� de population de la grille initiale (1 peu peupl�e, 10 surpeupl�e).
* Temporisation: d�lai en ms d'affichage des �tapes du cycle d'�volution des cellules.
* Difficult�: nombre de virus disponibles.
* Mode du jeu: le jeu peut �tre en tour par tour pour bien visualiser les �tapes de l'�volution, ou en mode automatique.
        