//
//  TeamRow.swift
//  iosApp
//
//  Created by Camilo Medina on 29/10/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TeamRow: View {
    var team: Team

    var body: some View {
        HStack() {
            HStack(spacing: 12.0) {
                RemoteImage(withURL: team.badge)
                Text(team.name)
            }
            Spacer()
        }
    }
}
