package com.lifegame.service;

import com.lifegame.model.Cycle;

interface IPlayCycleService {
    
	Cycle playFullTurn();
	
    Cycle playStepLife();
    
    Cycle playStepUpdateGrid();

}


