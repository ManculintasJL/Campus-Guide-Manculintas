#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include <conio.h>
#include <windows.h>
#include <fstream>
#include <algorithm>
#include <cmath>

using namespace std;

// =========================
// SETTINGS
// =========================
const int WIDTH = 60;
const int HEIGHT = 25;

const char PLAYER = '@';
const char ZOMBIE = 'Z';
const char BOSS = 'B';
const char BULLET = '*';
const char WALL = '#';
const char COIN = '$';
const char MEDKIT = '+';

// =========================
// STRUCTURES
// =========================
struct Entity {
    int x, y;
    int hp;
    int maxHp;
    int damage;
    bool alive;
    bool boss;
};

struct Bullet {
    int x, y;
    int dx, dy;
    int damage;
    bool alive;
};

// =========================
// GLOBAL VARIABLES
// =========================
Entity player;

vector<Entity> enemies;
vector<Bullet> bullets;

int score = 0;
int coins = 0;
int wave = 1;

int ammo = 12;
int maxAmmo = 12;
int reserveAmmo = 60;

int damage = 2;
int armor = 0;

int medkits = 2;
int maxMedkits = 5;

int highScore = 0;

bool running = true;
bool waveComplete = false;

int tickCounter = 0;

// =========================
// COLORS
// =========================
void color(int c) {
    SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), c);
}

void resetColor() {
    color(7);
}

// =========================
// CURSOR
// =========================
void hideCursor() {
    HANDLE console = GetStdHandle(STD_OUTPUT_HANDLE);

    CONSOLE_CURSOR_INFO cursorInfo;

    cursorInfo.dwSize = 1;
    cursorInfo.bVisible = FALSE;

    SetConsoleCursorInfo(console, &cursorInfo);
}

// =========================
// CLEAR SCREEN
// =========================
void clearScreen() {
    system("cls");
}

// =========================
// HIGH SCORE
// =========================
void loadHighScore() {
    ifstream file("highscore.txt");

    if (file) {
        file >> highScore;
    }
}

void saveHighScore() {
    ofstream file("highscore.txt");

    if (file) {
        file << highScore;
    }
}

// =========================
// RANDOM POSITION
// =========================
bool positionFree(int x, int y) {

    if (x <= 1 || x >= WIDTH - 2)
        return false;

    if (y <= 1 || y >= HEIGHT - 2)
        return false;

    if (x == player.x && y == player.y)
        return false;

    for (auto &e : enemies) {
        if (e.alive && e.x == x && e.y == y)
            return false;
    }

    return true;
}

// =========================
// SPAWN ENEMY
// =========================
void spawnEnemy() {

    int x, y;

    do {
        x = 2 + rand() % (WIDTH - 4);
        y = 2 + rand() % (HEIGHT - 4);

    } while (!positionFree(x, y));

    Entity enemy;

    enemy.x = x;
    enemy.y = y;

    enemy.boss = false;

    enemy.maxHp = 4 + wave;
    enemy.hp = enemy.maxHp;

    enemy.damage = 1 + wave / 5;

    enemy.alive = true;

    enemies.push_back(enemy);
}

// =========================
// SPAWN BOSS
// =========================
void spawnBoss() {

    int x, y;

    do {
        x = 2 + rand() % (WIDTH - 4);
        y = 2 + rand() % (HEIGHT - 4);

    } while (!positionFree(x, y));

    Entity boss;

    boss.x = x;
    boss.y = y;

    boss.boss = true;

    boss.maxHp = 30 + wave * 10;
    boss.hp = boss.maxHp;

    boss.damage = 3 + wave / 3;

    boss.alive = true;

    enemies.push_back(boss);
}

// =========================
// START WAVE
// =========================
void startWave() {

    enemies.clear();
    bullets.clear();

    waveComplete = false;

    int amount = 4 + wave * 2;

    for (int i = 0; i < amount; i++) {
        spawnEnemy();
    }

    // Every 5 waves = BOSS
    if (wave % 5 == 0) {
        spawnBoss();
    }
}

// =========================
// DRAW BORDER
// =========================
void drawBorder() {

    color(11);

    for (int x = 0; x < WIDTH; x++) {
        cout << '#';
    }

    cout << '\n';

    resetColor();
}

// =========================
// DRAW GAME
// =========================
void drawGame() {

    clearScreen();

    // =========================
    // TOP UI
    // =========================

    color(11);

    cout << "====================== NEON SURVIVOR ======================\n";

    resetColor();

    cout << "Wave: ";

    color(14);
    cout << wave;

    resetColor();

    cout << "   Score: ";

    color(10);
    cout << score;

    resetColor();

    cout << "   Coins: ";

    color(14);
    cout << coins;

    resetColor();

    cout << "   High Score: ";

    color(13);
    cout << highScore;

    resetColor();

    cout << "\n";

    // HP
    cout << "HP: ";

    color(12);

    cout << player.hp << "/" << player.maxHp;

    resetColor();

    cout << "   Armor: ";

    color(11);

    cout << armor;

    resetColor();

    cout << "   Ammo: ";

    color(10);

    cout << ammo << "/" << reserveAmmo;

    resetColor();

    cout << "   Medkits: ";

    color(13);

    cout << medkits;

    resetColor();

    cout << "\n\n";

    // =========================
    // GAME MAP
    // =========================

    for (int y = 0; y < HEIGHT; y++) {

        for (int x = 0; x < WIDTH; x++) {

            bool printed = false;

            // Border
            if (x == 0 || x == WIDTH - 1 ||
                y == 0 || y == HEIGHT - 1) {

                color(8);
                cout << '#';
                resetColor();

                continue;
            }

            // Player
            if (x == player.x && y == player.y) {

                color(10);
                cout << PLAYER;
                resetColor();

                printed = true;
            }

            // Bullets
            if (!printed) {

                for (auto &b : bullets) {

                    if (b.alive &&
                        b.x == x &&
                        b.y == y) {

                        color(14);
                        cout << BULLET;
                        resetColor();

                        printed = true;
                        break;
                    }
                }
            }

            // Enemies
            if (!printed) {

                for (auto &e : enemies) {

                    if (e.alive &&
                        e.x == x &&
                        e.y == y) {

                        if (e.boss) {

                            color(12);
                            cout << B;

                        } else {

                            color(4);
                            cout << Z;
                        }

                        resetColor();

                        printed = true;
                        break;
                    }
                }
            }

            if (!printed) {
                cout << ' ';
            }
        }

        cout << '\n';
    }

    // =========================
    // CONTROLS
    // =========================

    color(8);

    cout << "\n------------------------------------------------------------\n";

    resetColor();

    cout << "WASD Move | SPACE Shoot | R Reload | H Heal | E Upgrade | Q Quit\n";
}

// =========================
// MOVE PLAYER
// =========================
void movePlayer(int dx, int dy) {

    int nx = player.x + dx;
    int ny = player.y + dy;

    if (nx <= 0 || nx >= WIDTH - 1)
        return;

    if (ny <= 0 || ny >= HEIGHT - 1)
        return;

    // Don't walk into enemy
    for (auto &e : enemies) {

        if (e.alive &&
            e.x == nx &&
            e.y == ny) {

            return;
        }
    }

    player.x = nx;
    player.y = ny;
}

// =========================
// SHOOT
// =========================
void shoot() {

    if (ammo <= 0)
        return;

    ammo--;

    Bullet b;

    b.x = player.x;
    b.y = player.y;

    b.damage = damage;
    b.alive = true;

    // Ask direction
    cout << "\nDirection (W/A/S/D): ";

    char direction = _getch();

    if (direction == 'w') {
        b.dx = 0;
        b.dy = -1;
    }
    else if (direction == 's') {
        b.dx = 0;
        b.dy = 1;
    }
    else if (direction == 'a') {
        b.dx = -1;
        b.dy = 0;
    }
    else if (direction == 'd') {
        b.dx = 1;
        b.dy = 0;
    }
    else {
        ammo++;
        return;
    }

    bullets.push_back(b);
}

// =========================
// RELOAD
// =========================
void reload() {

    if (ammo == maxAmmo)
        return;

    if (reserveAmmo <= 0)
        return;

    int needed = maxAmmo - ammo;

    int amount = min(needed, reserveAmmo);

    ammo += amount;
    reserveAmmo -= amount;
}

// =========================
// HEAL
// =========================
void heal() {

    if (medkits <= 0)
        return;

    if (player.hp == player.maxHp)
        return;

    player.hp += 10;

    if (player.hp > player.maxHp)
        player.hp = player.maxHp;

    medkits--;
}

// =========================
// BULLET UPDATE
// =========================
void updateBullets() {

    for (auto &b : bullets) {

        if (!b.alive)
            continue;

        b.x += b.dx;
        b.y += b.dy;

        // Outside map
        if (b.x <= 0 ||
            b.x >= WIDTH - 1 ||
            b.y <= 0 ||
            b.y >= HEIGHT - 1) {

            b.alive = false;
            continue;
        }

        // Hit enemy
        for (auto &e : enemies) {

            if (!e.alive)
                continue;

            if (b.x == e.x &&
                b.y == e.y) {

                e.hp -= b.damage;

                b.alive = false;

                if (e.hp <= 0) {

                    e.alive = false;

                    if (e.boss) {

                        score += 500;
                        coins += 100;

                    } else {

                        score += 100;
                        coins += 10;
                    }

                    if (score > highScore) {
                        highScore = score;
                    }
                }

                break;
            }
        }
    }

    // Remove dead bullets
    bullets.erase(
        remove_if(
            bullets.begin(),
            bullets.end(),
            [](Bullet &b) {
                return !b.alive;
            }
        ),
        bullets.end()
    );
}

// =========================
// ENEMY MOVEMENT
// =========================
void updateEnemies() {

    for (auto &e : enemies) {

        if (!e.alive)
            continue;

        // Distance
        int dx = player.x - e.x;
        int dy = player.y - e.y;

        int distance =
            abs(dx) + abs(dy);

        // Attack
        if (distance <= 1) {

            int actualDamage =
                max(1, e.damage - armor);

            player.hp -= actualDamage;

            if (player.hp <= 0) {

                player.hp = 0;
                running = false;
            }

            continue;
        }

        // Move every few ticks
        if (tickCounter % 3 != 0)
            continue;

        int nx = e.x;
        int ny = e.y;

        if (abs(dx) > abs(dy)) {

            if (dx > 0)
                nx++;
            else
                nx--;

        } else {

            if (dy > 0)
                ny++;
            else
                ny--;
        }

        // Don't move onto player
        if (nx == player.x &&
            ny == player.y) {

            continue;
        }

        // Don't overlap enemies
        bool occupied = false;

        for (auto &other : enemies) {

            if (&other == &e)
                continue;

            if (other.alive &&
                other.x == nx &&
                other.y == ny) {

                occupied = true;
                break;
            }
        }

        if (!occupied) {

            e.x = nx;
            e.y = ny;
        }
    }
}

// =========================
// UPGRADE SHOP
// =========================
void shop() {

    while (true) {

        clearScreen();

        color(13);

        cout << "\n================== NEON SHOP ==================\n\n";

        resetColor();

        cout << "Coins: ";

        color(14);
        cout << coins;
        resetColor();

        cout << "\n\n";

        cout << "1. Increase Damage (+1)       - 50 coins\n";
        cout << "2. Increase Max HP (+10)      - 60 coins\n";
        cout << "3. Increase Magazine (+3)     - 70 coins\n";
        cout << "4. Increase Armor (+1)        - 100 coins\n";
        cout << "5. Buy Medkit                 - 30 coins\n";
        cout << "6. Exit Shop\n\n";

        cout << "Choose: ";

        char choice = _getch();

        if (choice == '1') {

            if (coins >= 50) {

                coins -= 50;
                damage++;

            }
        }

        else if (choice == '2') {

            if (coins >= 60) {

                coins -= 60;

                player.maxHp += 10;
                player.hp += 10;
            }
        }

        else if (choice == '3') {

            if (coins >= 70) {

                coins -= 70;

                maxAmmo += 3;
                ammo = maxAmmo;
            }
        }

        else if (choice == '4') {

            if (coins >= 100) {

                coins -= 100;

                armor++;
            }
        }

        else if (choice == '5') {

            if (coins >= 30 &&
                medkits < maxMedkits) {

                coins -= 30;
                medkits++;
            }
        }

        else if (choice == '6') {

            break;
        }
    }
}

// =========================
// CHECK WAVE
// =========================
void checkWave() {

    bool anyAlive = false;

    for (auto &e : enemies) {

        if (e.alive) {

            anyAlive = true;
            break;
        }
    }

    if (!anyAlive && !waveComplete) {

        waveComplete = true;

        score += wave * 100;

        coins += wave * 20;

        wave++;

        // Ammo reward
        reserveAmmo += 30;

        // Heal reward
        player.hp += 5;

        if (player.hp > player.maxHp)
            player.hp = player.maxHp;

        Sleep(800);

        startWave();
    }
}

// =========================
// INTRO
// =========================
void intro() {

    clearScreen();

    color(11);

    cout << R"(

███╗   ██╗███████╗ ██████╗ ███╗   ██╗
████╗  ██║██╔════╝██╔═══██╗████╗  ██║
██╔██╗ ██║█████╗  ██║   ██║██╔██╗ ██║
██║╚██╗██║██╔══╝  ██║   ██║██║╚██╗██║
██║ ╚████║███████╗╚██████╔╝██║ ╚████║
╚═╝  ╚═══╝╚══════╝ ╚═════╝ ╚═╝  ╚═══╝

███████╗██╗   ██╗██████╗ ██╗██╗   ██╗ █████╗ ██╗
██╔════╝██║   ██║██╔══██╗██║██║   ██║██╔══██╗██║
███████╗██║   ██║██████╔╝██║██║   ██║███████║██║
╚════██║██║   ██║██╔══██╗██║╚██╗ ██╔╝██╔══██║██║
███████║╚██████╔╝██║  ██║██║ ╚████╔╝ ██║  ██║██║
╚══════╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═══╝  ╚═╝  ╚═╝╚═╝

)";

    resetColor();

    cout << "\n";
    color(14);

    cout << "SURVIVE THE NIGHT.\n";

    resetColor();

    cout << "\n";

    cout << "WASD  = Move\n";
    cout << "SPACE = Shoot\n";
    cout << "R     = Reload\n";
    cout << "H     = Heal\n";
    cout << "E     = Shop\n";
    cout << "Q     = Quit\n";

    cout << "\nPress any key to start...";

    _getch();
}

// =========================
// GAME OVER
// =========================
void gameOver() {

    clearScreen();

    color(12);

    cout << "\n\n";
    cout << "============================================\n";
    cout << "                GAME OVER\n";
    cout << "============================================\n";

    resetColor();

    cout << "\nWave Reached : " << wave;
    cout << "\nScore        : " << score;
    cout << "\nCoins        : " << coins;
    cout << "\nHigh Score   : " << highScore;

    cout << "\n\n";

    if (score >= highScore) {

        color(14);
        cout << "NEW HIGH SCORE!\n";
        resetColor();
    }

    cout << "\nPress R to restart or Q to quit.";

    while (true) {

        char key = _getch();

        if (key == 'r' ||
            key == 'R') {

            break;
        }

        if (key == 'q' ||
            key == 'Q') {

            exit(0);
        }
    }
}

// =========================
// RESET GAME
// =========================
void resetGame() {

    player.x = WIDTH / 2;
    player.y = HEIGHT / 2;

    player.maxHp = 100;
    player.hp = 100;

    player.damage = 2;
    player.alive = true;
    player.boss = false;

    score = 0;
    coins = 0;

    wave = 1;

    damage = 2;
    armor = 0;

    maxAmmo = 12;
    ammo = maxAmmo;

    reserveAmmo = 60;

    medkits = 2;

    enemies.clear();
    bullets.clear();

    running = true;
    waveComplete = false;

    tickCounter = 0;

    startWave();
}

// =========================
// MAIN GAME LOOP
// =========================
void gameLoop() {

    while (running) {

        tickCounter++;

        // =========================
        // INPUT
        // =========================

        if (_kbhit()) {

            char key = _getch();

            if (key == 'w' ||
                key == 'W') {

                movePlayer(0, -1);
            }

            else if (key == 's' ||
                     key == 'S') {

                movePlayer(0, 1);
            }

            else if (key == 'a' ||
                     key == 'A') {

                movePlayer(-1, 0);
            }

            else if (key == 'd' ||
                     key == 'D') {

                movePlayer(1, 0);
            }

            else if (key == ' ') {

                shoot();
            }

            else if (key == 'r' ||
                     key == 'R') {

                reload();
            }

            else if (key == 'h' ||
                     key == 'H') {

                heal();
            }

            else if (key == 'e' ||
                     key == 'E') {

                shop();
            }

            else if (key == 'q' ||
                     key == 'Q') {

                running = false;
            }
        }

        // =========================
        // UPDATE
        // =========================

        updateBullets();

        updateEnemies();

        checkWave();

        // =========================
        // DRAW
        // =========================

        drawGame();

        // =========================
        // GAME SPEED
        // =========================

        Sleep(80);
    }
}

// =========================
// MAIN
// =========================
int main() {

    srand((unsigned)time(NULL));

    hideCursor();

    loadHighScore();

    while (true) {

        intro();

        resetGame();

        gameLoop();

        if (!running) {

            gameOver();
        }
    }

    saveHighScore();

    return 0;
}