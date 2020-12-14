package com.kirbydee.splooshkaboom.contract;

import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.tile.game.GameTile;
import com.kirbydee.splooshkaboom.model.tile.state.Bomb;
import com.kirbydee.splooshkaboom.model.tile.state.Squid;

public class GameContract {

    public interface Presenter {

        void reset();

        void onShoot(int row, int column);
    }

    public interface View {

        void onCounterChange(Counter count);

        void onSploosh(GameTile gameTile);

        void onKaboom(GameTile gameTile);

        void onDetonateSquid(Squid squid);

        void onDetonateBomb(Bomb bomb);

        void onWin(Counter counter);

        void onLoss();
    }
}
