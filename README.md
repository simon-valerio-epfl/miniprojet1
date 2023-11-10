# MP1 CS107

## 🎉 Modules supplémentaires 🎉

### CLI orienté utilisateur

Après avoir fini le projet nous avons codé dans le fichier `Main.java` un algorithme pour pouvoir facilement cacher du texte dans des images sur notre ordinateur.

![example](./example.png)

#### ⚙️ Paramètres ⚙️

Nous supportons actuellement les options suivantes :

**Cacher**

* emplacement de l'image de cover
* texte à cacher
* méthode de chiffrement **optionnelle** (`cbc` ou `vigenere`)
* clef de chiffrement (seulement si chiffrement activé)
* emplacement de l'image de sortie

**Révéler**

* emplacement de l'image source
* méthode de chiffrement **optionnelle** (`cbc` ou `vigenere`)
* clef de chiffrement (seulement si chiffrement activé)

Collecter ces paramètres auprès de l'utilisateur en gérant les cas limites s'est vite avéré très long, pénible et **très** répétitif.

Pour cela, nous avons décidé de développer un paquet `collector` pour faciliter la collecte des paramètres en gardant un certain niveau d'abstraction.

### Package Collector
 
#### Fonctionnement des fonctions collectSafeX

L'objectif des fonctions `collectSafeString`, `collectSafeBoolean` est de récupérer des données de l'utilisateur **tout en les validant** et les **nettoyant** (d'où le `safe` dans le nom).

Ces fonctions prennent en paramètre :
* une question
* une fonction **validator** (String -> Boolean), chargée de valider les données 
* une fonction **modifier** (String -> String|Boolean), chargée de nettoyer l'input de l'utilisateur, et de le renvoyer dans le bon format.

° par exemple dans le cas du modifier **MODIFIER_FILE_PATH_STRING**, celui-ci permet de convertir le chemin relatif de l'image en chemin absolu, s'il n'est pas fourni sous cette forme. 

### Package Helper

Nous avons ajouté ici une fonction `getExpectedString` (String, String[] -> String) qui prend en entrée une chaîne de caractère **proposée** par l'utilisateur, et une liste de chaînes de caractères **attendues**.  
Cette fonction renvoie la première chaine de caractères **attendue** qui est égale, en utilisant une comparaison non sensible à la casse, à la chaîne proposée par l'utilisateur.
