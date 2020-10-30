import SwiftUI
import shared

struct ContentView: View {
  @ObservedObject private(set) var viewModel: ViewModel

    var body: some View {
        NavigationView {
            listView()
            .navigationBarTitle("English Premier")
            .navigationBarItems(trailing:
                Button("Reload") {
                    self.viewModel.loadTeams(forceReload: true)
            })
        }
    }

    private func listView() -> AnyView {
        switch viewModel.teams {
        case .loading:
            return AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let teams):
            return AnyView(List(teams) { team in
                TeamRow(team: team)
            })
        case .error(let description):
            return AnyView(Text(description).multilineTextAlignment(.center))
        }
    }
}

extension ContentView {
    enum LoadableTeams {
        case loading
        case result([Team])
        case error(String)
    }

    class ViewModel: ObservableObject {
        let sdk: SportsSDK
        @Published var teams = LoadableTeams.loading

        init(sdk: SportsSDK) {
            self.sdk = sdk
            self.loadTeams(forceReload: false)
        }

       func loadTeams(forceReload: Bool) {
            self.teams = .loading
            sdk.getTeams(forceReload: forceReload, completionHandler: { teams, error in
                if let teams = teams {
                    self.teams = .result(teams)
                } else {
                    self.teams = .error(error?.localizedDescription ?? "error")
                }
            })
        }
    }
}

extension Team: Identifiable { }
