package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView playeronescore,playertwoscore,playerstatus;
    private Button[] buttons =new Button[9];
    private Button resetGame;
    private int playerOneScoreCount,playerTwoScoreCount,roundcount;
    boolean activeplayer;

    int[] gameState={2,2,2,2,2,2,2,2,2};

    int[][] winning={
            {0,1,2},{3,4,5},{6,7,8},
            {0,3,6},{1,4,7},{2,5,8},
            {7,5,3},{0,4,8}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playeronescore=(TextView) findViewById(R.id.playerOneScore);
        playertwoscore=(TextView) findViewById(R.id.playerTwoScore);
        playerstatus=(TextView) findViewById(R.id.playerStatus);
        resetGame=(Button) findViewById(R.id.reset);
        for(int i=0;i< buttons.length;i++)
        {
            String buttonID="btn_"+i;
            int resourseID=getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i]=(Button)findViewById(resourseID);
            buttons[i].setOnClickListener(this);
        }

        roundcount=0;
        playerOneScoreCount=0;
        playerTwoScoreCount=0;



    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals(""))
        {
            return;
        }
        String buttonID=view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer=Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activeplayer)
        {
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer]=0;
        }
        else
        {
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer]=1;
        }
        roundcount++;
        if(checkWinner())
        {
            if(activeplayer)
            {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won", Toast.LENGTH_SHORT).show();
                playAgain();
            }
            else
            {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won", Toast.LENGTH_SHORT).show();
                playAgain();
            }

        }
        else if(roundcount==9)
        {
            playAgain();
            Toast.makeText(this, "There is no winner", Toast.LENGTH_SHORT).show();
        }
        else
        {
            activeplayer=!activeplayer;
        }
        if(playerOneScoreCount>playerTwoScoreCount)
        {
            playerstatus.setText("Player One is Winning");
        }
        else if(playerTwoScoreCount>playerOneScoreCount)
        {
            playerstatus.setText("Player Two is Winning");
        }
        else
        {
            playerstatus.setText("Draw");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount=0;
                playerTwoScoreCount=0;
                playerstatus.setText("");
                updatePlayerScore();
            }
        });

    }

    public boolean checkWinner(){
        boolean winnerResult=false;
        for(int[] winning: winning)
        {
            if(gameState[winning[0]]==gameState[winning[1]]&&
                gameState[winning[1]]==gameState[winning[2]]&&
                    gameState[winning[0]]!=2)
            {
                winnerResult=true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore()
    {
        playeronescore.setText(Integer.toString(playerOneScoreCount));
        playertwoscore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain()
    {
        roundcount=0;
        activeplayer=true;
        for(int i=0;i<buttons.length;i++)
        {
            gameState[i]=2;
            buttons[i].setText("");
        }
    }



}