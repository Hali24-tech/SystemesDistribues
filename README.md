# Projet Systèmes Distribués - Horloges Vectorielles et Simulation

Ce projet implémente un système distribué simple utilisant des **horloges vectorielles (Vector Clocks)** pour garantir l'ordre causal des messages (algorithme *causal ordering* de Birman-Schiper-Stephenson).

## Fonctionnalités

*   **Communication P2P** : Les nœuds communiquent via des sockets TCP.
*   **Horloges Vectorielles** : Chaque nœud maintient une horloge vectorielle pour suivre les événements et gérer la causalité.
*   **Tampon (Buffer)** : Les messages arrivant "trop tôt" (violant la causalité) sont mis en tampon jusqu'à ce que les messages manquants arrivent.
*   **CLI** : Interface en ligne de commande pour interagir avec chaque nœud.

## Structure du Projet

*   `nodes.config` : Fichier de configuration définissant la topologie (ID, IP, Port).
*   `src/Projet/Main.java` : Point d'entrée. Charge la config, lance le serveur et le CLI.
*   `src/Projet/Node.java` : Représente un nœud, son état (horloge, buffer) et la logique de diffusion (`broadcast`).
*   `src/Projet/NetworkServer.java` : Écoute les connexions entrantes et traite les messages (livraison ou mise en tampon).
*   `src/Projet/VectorClock.java` : Implémentation logique de l'horloge vectorielle.

## Concepts Clés

### Causalité et Horloges
Dans un système distribué, l'ordre physique du temps est difficile à synchroniser. On utilise donc le temps logique.
Si un événement A cause B, alors `VC(A) < VC(B)`.
Si deux événements n'ont pas de lien causal, ils sont *concurrents*.

### Algorithme de Livraison
Un message $m$ envoyé par le nœud $j$ est livré au nœud $i$ si :
1.  Le nœud $i$ a reçu tous les messages précédents de $j$ (`VC_m[j] == VC_i[j] + 1`).
2.  Le nœud $i$ a reçu tous les messages que $j$ avait reçus avant d'envoyer $m$ (`VC_m[k] <= VC_i[k]` pour tout $k \neq j$).

## Comment Exécuter

### 1. Compilation
```bash
javac -d bin src/Projet/*.java src/module-info.java
```

### 2. Configuration
Modifiez `nodes.config` si nécessaire. Par défaut (local) :
```
0 127.0.0.1 5001
1 127.0.0.1 5002
2 127.0.0.1 5003
```

### 3. Lancement
Ouvrez 3 terminaux distincts :

**Terminal 1 (Nœud 0)**
```bash
java -cp bin Projet.Main 0 nodes.config
```

**Terminal 2 (Nœud 1)**
```bash
java -cp bin Projet.Main 1 nodes.config
```

**Terminal 3 (Nœud 2)**
```bash
java -cp bin Projet.Main 2 nodes.config
```

### 4. Utilisation
Dans chaque terminal, utilisez les commandes disponsibles :
- `send <message>` : Envoie un message à tous les autres nœuds.
- `status` : Affiche l'état actuel (Horloge, Buffer).
- `exit` : Quitte le programme.

### Exemple de Test Causal
1.  **Nœud 0** envoie "M1" (`send M1`).
2.  **Nœud 1** reçoit M1, puis envoie "M2" (`send M2`).
3.  Si **Nœud 2** reçoit M2 *avant* M1 (ce qui peut arriver avec des délais réseau, ou simulé), M2 sera mis en **Buffer**.
4.  Dès que **Nœud 2** reçoit M1, il livre M1, puis vérifie son buffer et livre M2 automatiquement.

